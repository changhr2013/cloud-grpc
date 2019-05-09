package com.changhr.cloud.apollo.client.openfeign.basic;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

/**
 * 测试
 *
 * @author changhr
 * @create 2019-05-09 9:55
 */
public class MyApp {

    public static void main(String[] args) {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        // 获取并打印这个仓库的贡献者列表
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.getLogin() + " (" + contributor.getContributions() + ")");
        }

    }
}
