package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.Customer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlatFilesFormattedConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

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
                                 .<Customer, Customer>chunk(10)
                                 .reader(customItemReader())
                                 .writer(customItemWriter())
                                 .build();
    }

    @Bean
    public ItemWriter<? super Customer> customItemWriter() {
        return new FlatFileItemWriterBuilder<Customer>()
            .name("flatFileWriter")
            .append(true)
            .resource(new ClassPathResource("customer.txt"))
            .formatted()
            .format("%-3s|%-2d|%-4s")
            .names(new String[]{"name", "age", "year"})
            .build();
    }

    @Bean
    public ItemReader<Customer> customItemReader() {
        List<Customer> customers = List.of(new Customer("kim", 26, "1997"),
            new Customer("lee", 25, "1998"), new Customer("son", 24, "1999"));

        ListItemReader<Customer> reader = new ListItemReader<>(customers);

        return reader;
    }
}
