package com.changhr.cloud.apollo.client.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author changhr
 * @create 2019-11-11 11:37
 */
public class SimpleRateLimiter {

    private final String RATE_LIMITER_INCR_KEY = "rate_limiter_incr_number";

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();

        Long incr = jedis.incr(RATE_LIMITER_INCR_KEY);
        String seq = String.format("%013d", incr);

        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        pipe.zadd(key, nowTs, seq);
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        Response<Long> count = pipe.zcard(key);
        pipe.expire(key, period + 1);
        pipe.exec();
        pipe.clear();
        return count.get() <= maxCount;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.116.128", 6379);
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("user-id-1", "test-method", 60, 5));
        }
    }
}
