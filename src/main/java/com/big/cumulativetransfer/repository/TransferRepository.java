package com.big.cumulativetransfer.repository;

import com.big.cumulativetransfer.model.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TransferRepository extends BaseRepository {

    @Value("${spring.profiles.active}")
    private String currency;


    public Long count() {
        final String query = "select count(*) from " + currency + "_transfer";
        return getTemplate(currency).queryForObject(query, Long.class);

    }

    public List<Transfer> findAll(Pageable pageable) {
        final String query = "SELECT a.address_id AS sender,\n"
                + "       b.address_id AS receiver,\n"
                + "       a.amount_in_wei as amount\n"
                + "FROM   eth_transfer a JOIN eth_transfer b\n"
                + "         ON a.tx_id = b.tx_id\n"
                + "AND a.index_in_tx = b.index_in_tx\n"
                + "WHERE  a.direction = 0 AND b.direction = 1  " +
                "limit " + pageable.getPageSize() + " offset " + pageable.getOffset() + ";";

        return getTemplate(currency).query(query, (rs, rowNum) ->
                Transfer.builder()
                        .sender(rs.getLong("sender"))
                        .receiver(rs.getLong("receiver"))
                        .amount(rs.getBigDecimal("amount"))
                        .build()
        );

    }


}
