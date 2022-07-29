package com.minchul.springbatchstudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomService {

    private int cnt = 0;

    public String doSomething() {
        log.info("This is CustomService.doSomething()");
        return "item" + cnt++;
    }
}
