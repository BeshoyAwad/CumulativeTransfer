package com.big.cumulativetransfer;

import com.big.cumulativetransfer.service.CumulativeTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class CumulativeTransferApplication implements CommandLineRunner {

    private final CumulativeTransferService transferService;

    public static void main(String[] args) {
        SpringApplication.run(CumulativeTransferApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        transferService.processTransfers();
    }
}
