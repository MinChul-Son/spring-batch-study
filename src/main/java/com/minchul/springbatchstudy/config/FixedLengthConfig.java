package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class FixedLengthConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, String>chunk(5)
                                 .reader(itemReader())
                                 .writer(items -> {
                                     log.info("items = {}", items);
                                 })
                                 .build();
    }

    @Bean
    public FlatFileItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
            .name("flatFile")
            .resource(new ClassPathResource("/customer.txt"))
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
            .targetType(Customer.class)
            .linesToSkip(1)
            .fixedLength()
            .addColumns(new Range(1, 5))
            .addColumns(new Range(6, 9))
            .addColumns(new Range(10, 11))
            .names("name", "year", "age")
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step2");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
}
