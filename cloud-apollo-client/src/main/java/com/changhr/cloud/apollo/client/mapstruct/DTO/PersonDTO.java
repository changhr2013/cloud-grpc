package com.changhr.cloud.apollo.client.mapstruct.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO
 *
 * @author changhr
 * @create 2019-05-31 11:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDTO {

    private Long id;
    private String name;

    /** 对应Person.User.age */
    private Integer age;

    private String email;

    /** 与 DO 里面的字段名称（birthday）不一致 */
    private Date birth;

    /** 对 DO 里面的字段（birthday）进行扩展，dateFormat 的形式 */
    private String birthDateFormat;

    /** 对 DO 里面的字段（birthday）进行扩展，expression 的形式 */
    private String birthExpressionFormat;

}
