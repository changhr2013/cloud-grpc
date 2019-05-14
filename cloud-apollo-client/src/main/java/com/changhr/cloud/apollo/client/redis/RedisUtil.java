package com.changhr.cloud.apollo.client.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Redis 工具类
 *
 * @author changhr
 * @date 2019/4/27
 */
@SuppressWarnings({"unused", "ConstantConditions", "WeakerAccess"})
@Component
public final class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(单位：秒)
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key 键值，不能为 null
     * @return 时间(单位 ： 秒) 返回 0 代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key 键
     * @return true：存在 false：不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存获取字符串
     *
     * @param key 键
     * @return 字符串值
     */
    public String getString(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true：成功 false：失败
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(单位：秒) time 要大于 0 如果 time 小于等于 0 将设置无限期
     * @return true：成功 false：失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于 0)
     * @return long
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于 0)
     * @return long
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ============================== hash ==============================

    /**
     * HashGet
     *
     * @param key     键 不能为 null
     * @param hashKey 项 不能为 null
     * @return 值
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取多个 hashKey 对应的值
     *
     * @param key      hash 表 key
     * @param hashKeys hash 表中记录的 key 集合
     * @return key 集合对应的 value 集合
     */
    public List<Object> hMGet(String key, List<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 获取 hashKey 对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值对
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hMSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(单位：秒)
     * @return true：成功 false：失败
     */
    public boolean hMSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张 hash 表中放入数据，如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true：成功 false：失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张 hash 表中放入数据，如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(单位：秒) 注意：如果已存在的 hash 表有时间，这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 hash 表中的值
     *
     * @param key  键 不能为 null
     * @param item 项 可以是多个，不能为 null
     * @return 被成功删除字段的数量
     */
    public long hDel(String key, Object... item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断 hash 表中是否有该项的值
     *
     * @param key  键 不能为 null
     * @param item 项 不能为 null
     * @return true：存在 false：不存在
     */
    public boolean hExists(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 为 hash 表 key 中的域 field 的值加上增量 increment
     * 增量也可以是负数，相当于对给定域进行减法操作
     *
     * @param key       键
     * @param field     项
     * @param increment 增量（可以为负数）
     * @return 执行 hIncrBy 命令后，hash 表 key 中域 field 的值
     */
    public double hIncrBy(String key, String field, double increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    // ============================== set ==============================

    /**
     * 根据 key 获取 Set 中的所有值
     *
     * @param key 键
     * @return Set<Object>
     */
    public Set<Object> setGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据 value 从一个 set 中查询，是否存在
     *
     * @param key   键
     * @param value 值
     * @return true：存在 false：不存在
     */
    public boolean setHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入 set 缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将 set 数据放入缓存
     *
     * @param key    键
     * @param time   时间(单位：秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setAddAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取 set 缓存的长度
     *
     * @param key 键
     * @return long
     */
    public long setSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为 value 的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ============================== zset ==============================

    /**
     * 用于将一个成员元素及其分数值加入到有序集当中
     *
     * @param key   键值
     * @param value 值
     * @param score 分数
     * @return Boolean
     */
    public Boolean zAdd(String key, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ZSet 计算有序集合中指定分数区间的成员数量
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return long
     */
    public long zCount(String key, long min, long max) {
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ZSet 计算集合中元素的数量
     *
     * @param key 键值
     * @return 当 key 存在且是有序集合类型时，返回有序集的基数，当 key 不存在时，返回 0
     */
    public long zCard(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集中的一个或多个成员，不存在的成员将被忽略
     *
     * @param key    键值
     * @param values 集合成员
     * @return 被成功移除的成员的数量，不包括被忽略的成员
     */
    public long zRem(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集中，指定排名（rank）区间内的所有成员
     *
     * @param key   键值
     * @param start 起始索引
     * @param end   结束索引
     * @return 被移除成员的数量
     */
    public long zRemRangeByRank(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集中，指定分数（score）区间内的所有成员
     *
     * @param key 键值
     * @param min 最小分数
     * @param max 最大分数
     * @return 被移除成员的数量
     */
    public long zRemRangeByScore(String key, long min, long max) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ZSet score 的增加或者减少
     * 可以传递一个负数值，让分数减去相应的值
     *
     * @param key   键
     * @param value value
     * @param score score
     * @return member 成员的新分数值
     */
    public double zIncrby(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ZSet 查找 value 对应的 score
     *
     * @param key   键
     * @param value value
     * @return double
     */
    public double zScore(String key, String value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ZSet 判断 value 在 ZSet 中的排名
     * 其中有序集成员按分数值递增（从小到大）排序
     *
     * @param key   有序集（ZSet）的 key
     * @param value 成员
     * @return 返回 member 的正序排名
     */
    public long zRank(String key, String value) {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ZSet 判断有序集中成员的排名
     * 其中有序集成员按分数值递减（从大到小）排序
     *
     * @param key   有序集（ZSet）的 key
     * @param value 成员
     * @return 返回 member 的倒序排名
     */
    public long zRevRank(String key, String value) {
        try {
            return redisTemplate.opsForZSet().reverseRank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 指定区间内，有序集成员的列表，score 小的在前面
     */
    public Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询集合中指定顺序的值和 score，0 -1 表示获取全部的集合内容
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 指定区间内，带有分数值的有序集成员的列表，score 小的在前面
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScore(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定分数区间内的所有成员
     * 有序集成员按分数值递减（从小到大）的次序排列
     *
     * @param key 键值
     * @param min 最小分数值
     * @param max 最大分数值
     * @return 指定区间内，有序集成员的列表
     */
    public Set<Object> zRangeByScore(String key, int min, int max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询集合中指定顺序的值
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 指定区间内，有序集成员的列表，score 大的在前面
     */
    public Set<Object> zRevRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询集合中指定顺序的值和 score，0 -1 表示获取全部的集合内容
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 指定区间内，带有分数值的有序集成员的列表，score 大的在前面
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeWithScore(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定分数区间内的所有成员
     * 有序集成员按分数值递减（从大到小）的次序排列
     *
     * @param key 键值
     * @param min 最小分数值
     * @param max 最大分数值
     * @return 指定区间内，有序集成员的列表
     */
    public Set<Object> zRevRangeByScore(String key, int min, int max) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================== list ==============================

    /**
     * 获取 list 缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List<Object>
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 list 缓存的长度
     *
     * @param key 键
     * @return long
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引获取 list 中的值
     *
     * @param key   键
     * @param index 索引 index >= 0 时，0 表头，1 第二个元素，依次类推；
     *              index < 0 时，-1，表尾，-2 倒数第二个元素，依次类推
     * @return Object
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(单位：秒)
     * @return boolean
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(单位：秒)
     * @return boolean
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改 list 中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除 N 个值为 value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}