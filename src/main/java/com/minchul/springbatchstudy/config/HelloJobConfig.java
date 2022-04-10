package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.CustomTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                                .start(helloStep1())
                                .next(helloStep2())
                                .next(helloStep3())
                                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step1");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                                 .tasklet((contribution, chunkContext) -> {
                                     log.info("Hello Spring Batch - step2");

                                     ExecutionContext jobExecutionContext = contribution.getStepExecution()
                                                                                     .getJobExecution()
                                                                                     .getExecutionContext();
                                     ExecutionContext stepExecutionContext = contribution.getStepExecution()
                                                                                     .getExecutionContext();

                                     log.info("jobName: {}", jobExecutionContext.get("jobName"));
                                     log.info("stepName: {}", stepExecutionContext.get("stepName"));

                                     String jobName = chunkContext.getStepContext().getJobName();
                                     String stepName = chunkContext.getStepContext()
                                                                   .getStepExecution()
                                                                   .getStepName();

                                     if (jobExecutionContext.get("jobName") == null) {
                                         jobExecutionContext.put("jobName", jobName);
                                     }

                                     if (stepExecutionContext.get("stepName") == null) {
                                         stepExecutionContext.put("stepName", stepName);
                                     }

                                     log.info("jobName: {}", jobExecutionContext.get("jobName"));
                                     log.info("stepName: {}", stepExecutionContext.get("stepName"));

                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step helloStep3() {
        return stepBuilderFactory.get("helloStep3")
                                 .tasklet(new CustomTasklet())
                                 .build();
    }
}
