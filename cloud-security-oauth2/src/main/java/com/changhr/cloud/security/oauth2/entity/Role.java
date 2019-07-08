package com.changhr.cloud.security.oauth2.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author changhr
 */
@Data
public class Role implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}