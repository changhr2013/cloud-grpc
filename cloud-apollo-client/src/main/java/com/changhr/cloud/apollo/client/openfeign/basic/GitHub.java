package com.changhr.cloud.apollo.client.openfeign.basic;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * 远程 API 接口
 *
 * @author changhr
 * @create 2019-05-09 9:56
 */
public interface GitHub {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
