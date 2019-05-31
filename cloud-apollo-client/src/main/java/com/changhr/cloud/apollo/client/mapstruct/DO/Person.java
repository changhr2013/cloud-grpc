package com.changhr.cloud.apollo.client.mapstruct.DO;

import lombok.*;

import java.util.Date;

/**
 * test
 *
 * @author changhr
 * @create 2019-05-31 11:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private Long id;
    private String name;
    private String email;
    private Date birthday;
    private User User;
}
