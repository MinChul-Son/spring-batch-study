package com.minchul.springbatchstudy;

import com.minchul.springbatchstudy.domain.Member;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomItemReader implements ItemReader<Member> {

    private List<Member> members;

    public CustomItemReader(List<Member> members) {
        this.members = members;
    }

    @Override
    public Member read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!members.isEmpty()) {
            return members.remove(0);
        }

        return null;
    }
}
