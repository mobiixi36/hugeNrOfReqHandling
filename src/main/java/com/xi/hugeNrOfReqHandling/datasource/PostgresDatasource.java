package com.xi.hugeNrOfReqHandling.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xichen created on 11/02/2020
 */

@Configuration
public class PostgresDatasource {

    @Bean
    @ConfigurationProperties("app.datasource") //it refers to properties in /resources/application.yml
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
