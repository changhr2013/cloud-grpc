package com.changhr.cloud.apollo.client.mapstruct.convert;

import com.changhr.cloud.apollo.client.mapstruct.DO.Person;
import com.changhr.cloud.apollo.client.mapstruct.DTO.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * mapper
 *
 * @author changhr
 * @create 2019-05-31 11:21
 */
@Mapper
public interface PersonConverter {
    PersonConverter INSTANCE = Mappers.getMapper(PersonConverter.class);

    /**
     * 将 Person 转换为 PersonDTO
     * @param person Person
     * @return PersonDTO
     */
    @Mappings({
            @Mapping(source = "birthday", target = "birth"),
            @Mapping(source = "birthday", target = "birthDateFormat", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "birthExpressionFormat",
                    expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(person.getBirthday(),\"yyyy-MM-dd HH:mm:ss\"))"),
            @Mapping(source = "user.age", target = "age"),
            @Mapping(target = "email", ignore = true),
    })
    PersonDTO convertToPersonDTO(Person person);

    /**
     * 将 List<Person> 转换为 List<PersonDTO>
     *
     * @param people List<Person>
     * @return List<PersonDTO>
     */
    List<PersonDTO> convertToPersonDTO(List<Person> people);
}
