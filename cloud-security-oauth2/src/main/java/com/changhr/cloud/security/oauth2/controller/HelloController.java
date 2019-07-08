package com.changhr.cloud.security.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changhr
 */
@RestController
public class HelloController {

    @GetMapping("/hi")
    public String hi(String name) {
        return "hi , " + name;
    }
}