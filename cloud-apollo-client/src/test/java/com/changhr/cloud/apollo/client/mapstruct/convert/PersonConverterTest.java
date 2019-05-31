package com.changhr.cloud.apollo.client.mapstruct.convert;

import com.changhr.cloud.apollo.client.mapstruct.DO.Person;
import com.changhr.cloud.apollo.client.mapstruct.DO.User;
import com.changhr.cloud.apollo.client.mapstruct.DTO.PersonDTO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PersonConverterTest {
    @Test
    public void test(){
        Person person = new Person(1L, "kangkang", "kangkang.edu.com", new Date(), new User(18));
        PersonDTO personDTO = PersonConverter.INSTANCE.convertToPersonDTO(person);
        System.out.println(personDTO);

        String format = DateFormatUtils.format(personDTO.getBirth(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(format);


    }

}