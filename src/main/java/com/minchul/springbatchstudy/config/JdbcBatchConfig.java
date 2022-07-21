package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.CustomerV2;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<CustomerV2, CustomerV2>chunk(10)
                                 .reader(customItemReader())
                                 .writer(customItemWriter())
                                 .build();
    }

    @Bean
    public ItemWriter<? super CustomerV2> customItemWriter() {
        return new JdbcBatchItemWriterBuilder<CustomerV2>()
            .dataSource(dataSource)
            .sql("insert into customer values (:id, :firstName, :lastName, :birthDate)")
            .beanMapped()
            .build();
    }

    @Bean
    public ItemReader<CustomerV2> customItemReader() {
        List<CustomerV2> customers = List.of(new CustomerV2(1L, "spring", "kim", "19970101"),
            new CustomerV2(2L, "spring", "park", "19960101"), new CustomerV2(3L, "spring", "lee", "19950101"));

        ListItemReader<CustomerV2> reader = new ListItemReader<>(customers);

        return reader;
    }
}
