package com.big.cumulativetransfer.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class CumulativeTransferRepository extends BaseRepository {

    @Transactional
    public void batchInsert(String currency, String filePath) {

        String query = "LOAD DATA LOCAL INFILE '" + filePath + "' REPLACE INTO TABLE cumul_transfer " +
                "FIELDS TERMINATED BY ',' " +
                "ENCLOSED BY '\"' " +
                "LINES TERMINATED BY '\\\\n' " +
                "IGNORE 1 LINES; ";

        try {
            getTemplate(currency).execute(query);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
