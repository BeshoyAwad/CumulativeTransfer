package com.big.cumulativetransfer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseRepository {

    @Autowired
    @Qualifier("ethJdbcTemplate")
    protected JdbcTemplate ethJdbcTemplate;

    @Autowired
    @Qualifier("xrpJdbcTemplate")
    protected JdbcTemplate xrpJdbcTemplate;

    @Autowired
    @Qualifier("xlmJdbcTemplate")
    protected JdbcTemplate xlmJdbcTemplate;

    protected JdbcTemplate getTemplate(String currency) {
        switch (currency.toLowerCase()) {
            case "xrp":
                return xrpJdbcTemplate;
            case "xlm":
                return xlmJdbcTemplate;
            default:
                return ethJdbcTemplate;
        }
    }

}
