package com.minchul.springbatchstudy.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CompositionItemConfig {

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
                                 .<String, String>chunk(10)
                                 .reader(() -> "hello")
                                 .processor(customItemProcessor())
                                 .writer(items -> log.info("Items = {}", items))
                                 .build();
    }

    @Bean
    public ItemProcessor<? super String, String> customItemProcessor() {
        List itemProcessors = List.of(new CustomItemProcessorV3(), new CustomItemProcessorV4());

        return new CompositeItemProcessorBuilder<>()
            .delegates(itemProcessors)
            .build();
    }
}
