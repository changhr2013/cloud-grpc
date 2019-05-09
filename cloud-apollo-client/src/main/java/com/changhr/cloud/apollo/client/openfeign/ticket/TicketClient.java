package com.changhr.cloud.apollo.client.openfeign.ticket;

import feign.Feign;
import feign.okhttp.OkHttpClient;

/**
 * ticket client
 *
 * @author changhr
 * @create 2019-05-09 14:30
 */
public class TicketClient {

    public static TicketApi connect() {
        return Feign.builder()
                .client(new OkHttpClient())
                .target(TicketApi.class, "http://192.168.116.128:9090");
    }
}
