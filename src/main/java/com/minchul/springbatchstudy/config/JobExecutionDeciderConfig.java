package com.minchul.springbatchstudy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobExecutionDeciderConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step1())
                                .next(decider())
                                .from(decider()).on("ODD").to(oddStep())
                                .from(decider()).on("EVEN").to(evenStep())
                                .end()
                                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new CustomDecider();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step1");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - evenStep");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - oddStep");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
