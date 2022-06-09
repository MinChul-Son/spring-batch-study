package com.minchul.springbatchstudy;

import com.minchul.springbatchstudy.domain.Member;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Member, Member> {

    @Override
    public Member process(Member item) throws Exception {
        return new Member(item.name().toUpperCase());
    }
}
