package com.minchul.springbatchstudy.config;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class CustomBatchConfig extends BasicBatchConfigurer {

    private final DataSource dataSource;

    protected CustomBatchConfig(
        BatchProperties properties,
        DataSource dataSource,
        TransactionManagerCustomizers transactionManagerCustomizers) {
        super(properties, dataSource, transactionManagerCustomizers);
        this.dataSource = dataSource;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionManager(getTransactionManager());
        factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factoryBean.setTablePrefix("SYSTEM_");

        return factoryBean.getObject();
    }
}
