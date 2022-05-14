package com.minchul.springbatchstudy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlowJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                                .start(step1())
                                .on("COMPLETED")
                                .to(step2())
                                .from(step1())
                                .on("FAILED")
                                .to(step3())
                                .end()
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step1");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step2");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step3");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2())
                   .next(step3())
                   .end();

        return flowBuilder.build();
    }
}
