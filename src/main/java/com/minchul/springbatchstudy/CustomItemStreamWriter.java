package com.minchul.springbatchstudy;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

@Slf4j
public class CustomItemStreamWriter implements ItemStreamWriter<String> {

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        log.info("ItemWriter - Resource is opened");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        log.info("ItemWriter - Update");
    }

    @Override
    public void close() throws ItemStreamException {
        log.info("ItemWriter - Resource is closed");
    }

    @Override
    public void write(List<? extends String> items) throws Exception {
        log.info("ItemWriter - Write");
        items.forEach(item -> log.info(item));
    }
}
