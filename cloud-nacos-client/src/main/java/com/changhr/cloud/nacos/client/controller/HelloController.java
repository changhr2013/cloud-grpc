package com.changhr.cloud.nacos.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello nacos sample
 *
 * @author changhr
 * @create 2019-06-06 14:13
 */
@RestController
@RefreshScope
@RequestMapping("/hello")
public class HelloController {

    @Value("${hello.message.default}")
    private String message = "Nice!";

    @GetMapping("/{text}")
    public String hello(@PathVariable("text") String text) {
        return "Hello Nacos! " + message + " " + text;
    }

}
