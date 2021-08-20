package com.clone.ecommerce.dbinitalization;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

    private HikariDataSource dataSource = new HikariDataSource();

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer maxPoolSize;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private Long connectionTimeout;

    @Value("${spring.datasource.hikari.minimumIdle}")
    private Integer minimumIdle;

    @Value("${spring.datasource.hikari.idleTimeout}")
    private Long idleTimeout;

    @Value("${spring.datasource.dbservice.name}")
    private String dbServiceName;

    @Value("${spring.datasource.dbservice.port}")
    private String dbServicePort;

    @Bean
    JdbcTemplate jdbcTemplate() {
        String username = "root";
        String password = "root";

        StringBuilder mysqlUrl = new StringBuilder();
        mysqlUrl.append("jdbc:mysql://");
        mysqlUrl.append(dbServiceName);
        mysqlUrl.append(":");
        mysqlUrl.append(dbServicePort);
        mysqlUrl.append("/");
        mysqlUrl.append("?createDatabaseIfNotExist=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true");
        String url = mysqlUrl.toString();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setConnectionTimeout(connectionTimeout);

        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager txManager(){
        return new DataSourceTransactionManager(dataSource);
    }
}
