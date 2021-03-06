package com.minchul.springbatchstudy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleFlowConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .start(flow1())
                                .on("COMPLETED").to(flow2())
                                .from(flow1())
                                .on("FAILED").to(flow3())
                                .end()
                                .build();
    }

    @Bean
    public Flow flow1() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("flow1");
        builder.start(step1())
               .next(step2())
               .end();

        return builder.build();
    }

    @Bean
    public Flow flow2() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("flow2");
        builder.start(flow3())
               .next(step5())
               .next(step6())
               .end();

        return builder.build();
    }

    @Bean
    public Flow flow3() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("flow3");
        builder.start(step3())
               .next(step4())
               .end();

        return builder.build();
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
    public Step step4() {
        return stepBuilderFactory.get("step4")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step4");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step5");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step step6() {
        return stepBuilderFactory.get("step6")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step6");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
}
