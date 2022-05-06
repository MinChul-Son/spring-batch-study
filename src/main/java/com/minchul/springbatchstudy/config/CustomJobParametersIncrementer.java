package com.minchul.springbatchstudy.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class CustomJobParametersIncrementer implements JobParametersIncrementer {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(JobParameters parameters) {
        String id = FORMAT.format(new Date());
        return new JobParametersBuilder().addString("run.id", id).toJobParameters();
    }
}
