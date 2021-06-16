package com.big.cumulativetransfer.service.impl;

import com.big.cumulativetransfer.model.Address;
import com.big.cumulativetransfer.model.CumulativeTransfer;
import com.big.cumulativetransfer.model.Transfer;
import com.big.cumulativetransfer.repository.CumulativeTransferRepository;
import com.big.cumulativetransfer.repository.TransferRepository;
import com.big.cumulativetransfer.service.CumulativeTransferService;
import com.big.cumulativetransfer.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.big.cumulativetransfer.model.CumulativeTransferData.addressesData;
import static com.big.cumulativetransfer.model.CumulativeTransferData.transfersData;

@Slf4j
@RequiredArgsConstructor
@Service
public class CumulativeTransferServiceImpl implements CumulativeTransferService {

    private final TransferRepository transferRepository;
    private final CumulativeTransferRepository cumulativeTransferRepository;

    @Value("${spring.profiles.active}")
    private String currency;

    @Override
    public void processTransfers() {
        Long count = transferRepository.count();

        log.info("Start to process {} transfer(s)", count);

        int pages = (int) Math.ceil(count / 10000d);

        for (int i = 0; i < pages; i++) {

            log.info("Start to process from {} to {}", (i * 10000) + 1, (i + 1) * 10000);

            List<Transfer> transfers = transferRepository.findAll(PageRequest.of(i, 10000));

            transfers.forEach(transfer -> {
                putTransferData(transfer);
                putReceiverData(transfer);
                putSenderData(transfer);
            });
            
            if (transfersData.size() >= 10000) {
                CsvUtil.createClusterCsv(currency + "_transfers.csv", new ArrayList<>(transfersData.values()));
                cumulativeTransferRepository.batchInsert(currency, currency + "_transfers.csv");
                transfersData.clear();
            }

        }

    }

    private void putTransferData(Transfer transfer) {
        CumulativeTransfer cumulativeTransfer;

        if (transfersData.contains(transfer.getSender(), transfer.getReceiver())) {
            cumulativeTransfer = transfersData
                    .get(transfer.getSender(), transfer.getReceiver());
            cumulativeTransfer
                    .setCumNative(cumulativeTransfer.getCumNative().add(transfer.getAmount()));
        } else {
            cumulativeTransfer = new CumulativeTransfer();
            cumulativeTransfer.setSender(transfer.getSender());
            cumulativeTransfer.setReceiver(transfer.getReceiver());
            cumulativeTransfer.setCumNative(transfer.getAmount());
        }

        transfersData
                .put(transfer.getSender(), transfer.getReceiver(), cumulativeTransfer);
    }

    private void putReceiverData(Transfer transfer) {
        Address address;


        if (addressesData.containsKey(transfer.getReceiver())) {
            address = addressesData.get(transfer.getReceiver());
            address.setNativeTo(addressesData.get(transfer.getReceiver()).getNativeTo()
                    .add(transfer.getAmount()));
        } else {
            address = new Address();
            address.setId(transfer.getReceiver());
            address.setNativeTo(transfer.getAmount());
        }

        addressesData.put(address.getId(), address);
    }

    private void putSenderData(Transfer transfer) {
        Address address;

        if (addressesData.containsKey(transfer.getSender())) {
            address = addressesData.get(transfer.getSender());
            address.setNativeFrom(addressesData.get(transfer.getSender()).getNativeFrom()
                    .add(transfer.getAmount()));
        } else {
            address = new Address();
            address.setId(transfer.getSender());
            address.setNativeFrom(transfer.getAmount());
        }
        addressesData.put(address.getId(), address);
    }
}
