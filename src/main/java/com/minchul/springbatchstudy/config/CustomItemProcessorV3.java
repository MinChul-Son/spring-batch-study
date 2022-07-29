package com.minchul.springbatchstudy.config;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessorV3 implements ItemProcessor<String, String> {

    @Override
    public String process(String item) throws Exception {
        return item.toUpperCase();
    }
}
