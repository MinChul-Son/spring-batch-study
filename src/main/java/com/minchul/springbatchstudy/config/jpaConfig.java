package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.CustomerV2;
import com.minchul.springbatchstudy.domain.Person;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class jpaConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory em;

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
                                 .<CustomerV2, Person>chunk(10)
                                 .reader(customItemReader())
                                 .processor(customItemProcessor())
                                 .writer(customItemWriter())
                                 .build();
    }

    @Bean
    public ItemProcessor<? super CustomerV2, ? extends Person> customItemProcessor() {
        return new CustomItemProcessor2();
    }

    @Bean
    public ItemWriter<? super Person> customItemWriter() {
        return new JpaItemWriterBuilder<Person>()
            .usePersist(true)
            .entityManagerFactory(em)
            .build();
    }

    @Bean
    public ItemReader<CustomerV2> customItemReader() {
        List<CustomerV2> customers = List.of(new CustomerV2(1L, "spring", "kim", "19970101"),
            new CustomerV2(2L, "spring", "park", "19960101"),
            new CustomerV2(3L, "spring", "lee", "19950101"));

        ListItemReader<CustomerV2> reader = new ListItemReader<>(customers);

        return reader;
    }
}
