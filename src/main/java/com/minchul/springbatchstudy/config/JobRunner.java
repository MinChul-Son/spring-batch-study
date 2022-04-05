package com.minchul.springbatchstudy.config;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
            .addString("name", "user1")
            .addLong("seq", 2L)
            .addDate("Date", new Date())
            .addDouble("height", 182.5)
            .toJobParameters());
    }
}
