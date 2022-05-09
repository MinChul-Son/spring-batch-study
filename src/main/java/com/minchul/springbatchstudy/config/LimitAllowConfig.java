package com.minchul.springbatchstudy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class LimitAllowConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step1");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .allowStartIfComplete(true)
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step2");
                                     throw new RuntimeException("Failed Step2");
                                 })
                                 .startLimit(3)
                                 .build();
    }
}
