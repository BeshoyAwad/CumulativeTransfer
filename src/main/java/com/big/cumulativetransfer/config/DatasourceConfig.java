package com.big.cumulativetransfer.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {

    @Bean(name = "ethDataSource")
    @ConfigurationProperties(prefix = "spring.eth")
    public DataSource ethDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ethJdbcTemplate")
    public JdbcTemplate ethJdbcTemplate(@Qualifier("ethDataSource") DataSource ethDataSource) {
        return new JdbcTemplate(ethDataSource);
    }

    @Bean(name = "xlmDataSource")
    @ConfigurationProperties(prefix = "spring.xlm")
    public DataSource xlmDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "xlmJdbcTemplate")
    public JdbcTemplate xlmJdbcTemplate(@Qualifier("xlmDataSource") DataSource xlmDataSource) {
        return new JdbcTemplate(xlmDataSource);
    }

    @Bean(name = "xrpDataSource")
    @ConfigurationProperties(prefix = "spring.xrp")
    public DataSource xrpDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "xrpJdbcTemplate")
    public JdbcTemplate xrpJdbcTemplate(@Qualifier("xrpDataSource") DataSource xrpDataSource) {
        return new JdbcTemplate(xrpDataSource);
    }

}
