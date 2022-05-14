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

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TransitionConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .start(step1())
                                    .on("FAILED")
                                    .to(step2())
                                    .on("FAILED")
                                    .stop()
                                .from(step1())
                                    .on("*")
                                    .to(step3())
                                    .next(step4())
                                .from(step2())
                                    .on("*")
                                    .to(step5())
                                .end()
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Spring Batch - step1");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Spring Batch - step2");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Spring Batch - step3");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Spring Batch - step4");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Spring Batch - step5");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
}
