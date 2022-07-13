package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.CustomerV2;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcPagingConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                                 .<CustomerV2, CustomerV2>chunk(5)
                                 .reader(customItemReader())
                                 .writer(items -> {
                                     log.info("items = {}", items);
                                 })
                                 .build();
    }

    @Bean
    public ItemReader<CustomerV2> customItemReader() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "A%");

        return new JdbcPagingItemReaderBuilder<CustomerV2>()
            .name("jdbcPagingReader")
            .pageSize(10)
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(CustomerV2.class))
            .queryProvider(createQueryProvider())
            .parameterValues(parameters)
            .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("id, firstName, lastName, birthDate");
        queryProvider.setFromClause("from Person");
        queryProvider.setWhereClause("where firstName like :firstName");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }
}
