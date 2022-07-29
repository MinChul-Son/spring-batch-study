package com.minchul.springbatchstudy.config;

import com.minchul.springbatchstudy.domain.CustomerV2;
import com.minchul.springbatchstudy.domain.Person;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<CustomerV2, Person> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Person process(CustomerV2 item) throws Exception {
        Person person = modelMapper.map(item, Person.class);
        return person;
    }
}
