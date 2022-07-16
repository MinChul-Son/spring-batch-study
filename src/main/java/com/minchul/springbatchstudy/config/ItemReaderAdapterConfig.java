package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.service.CustomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ItemReaderAdapterConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .<String, String>chunk(10)
                                 .reader(customItemReader())
                                 .writer(items -> log.info("items = {}", items))
                                 .build();
    }

    @Bean
    public ItemReader<String> customItemReader() {
        ItemReaderAdapter<String> reader = new ItemReaderAdapter<>();
        reader.setTargetObject(new CustomService());
        reader.setTargetMethod("doSomething");

        return reader;
    }
}
