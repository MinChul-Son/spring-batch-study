package com.minchul.springbatchstudy.config;

import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobInstanceConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                JobParameters jobParameters = contribution.getStepExecution()
                                                          .getJobExecution()
                                                          .getJobParameters();

                jobParameters.getString("name");
                jobParameters.getLong("seq");
                jobParameters.getDate("date");
                jobParameters.getDouble("height");

                Map<String, Object> parameters = chunkContext.getStepContext()
                                                                 .getJobParameters();

                log.info("step1");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                log.info("step2");
                return RepeatStatus.FINISHED;
            }).build();
    }
}
