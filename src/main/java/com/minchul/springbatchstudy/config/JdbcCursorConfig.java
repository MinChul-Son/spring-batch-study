package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.Person;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcCursorConfig {

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
                                 .<Person, Person>chunk(10)
                                 .reader(customItemReader())
                                 .writer(items -> {
                                     log.info("items = {}", items);
                                 })
                                 .build();
    }

    @Bean
    public ItemReader<Person> customItemReader() {
        return new JdbcCursorItemReaderBuilder<Person>()
            .name("jdbcCursorItemReader")
            .fetchSize(10)
            .sql("select id, firstName, lastName, birthDate from person where firstName like ? order by lastName, firstName")
            .beanRowMapper(Person.class)
            .queryArguments("A%")
            .dataSource(dataSource)
            .build();
    }
}
