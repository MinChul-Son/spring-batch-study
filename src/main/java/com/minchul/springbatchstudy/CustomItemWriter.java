package com.minchul.springbatchstudy;

import com.minchul.springbatchstudy.domain.Member;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class CustomItemWriter implements ItemWriter<Member> {

    @Override
    public void write(List<? extends Member> items) throws Exception {
        items.forEach(member -> log.info("Hello Writer - Member name is {}", member));
    }
}
