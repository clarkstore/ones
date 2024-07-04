/*
 *
 *  * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.ones.common.redis.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Clark
 * @version 2023-04-23
 */
public class OsRedisUtils {
    /**
     * 注入redisTemplate bean
     */
    @Getter
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     *
     * @param key 键 不能为null
     * @return 时间(秒)
     */
    public long getExpire(String key) {
        return this.redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 删除指定前缀的缓存：参数不可为空
     * @param prefix 前缀
     */
    public void delByPrefix(String prefix) {
        if (StrUtil.isBlank(prefix)) {
            return;
        }
        Set<String> keys = this.redisTemplate.keys(prefix + "*");
        if (CollUtil.isNotEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }

    /**
     * 删除多层Key：模糊匹配
     * @param prefix 参数可为空
     * @param key 参数不可为空
     */
    public void delByPrefixAndKey(String prefix, String key) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        String queryKey = "*" + key;
        if (StrUtil.isNotBlank(prefix)) {
            queryKey = prefix + queryKey;
        }

        Set<String> keys = this.redisTemplate.keys(queryKey);
        if (CollUtil.isNotEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        Set<String> keys = this.redisTemplate.keys("*");
        this.redisTemplate.delete(keys);
    }

    /**
     * 取特定前缀下key集合
     * @param prefix
     * @return
     */
    public Set<String> getKeys(String prefix) {
        Set<String> keys = this.redisTemplate.keys(prefix + "*");
        return keys;
    }
    // ============================String(字符串)=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return String值(推荐JSON格式存储时使用)
     */
    public String getStr(String key) {
        Object res = this.get(key);
        return res == null ? null : res.toString();
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入:key存在就覆盖，不存在新增
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间:key存在就覆盖，不存在新增
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
    }

    /**
     * 如果key不存在则新增，key存在不做任何操作
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setIfAbsent(String key, Object value) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果key不存在则新增，key存在不做任何操作，并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setIfAbsent(String key, Object value, long time) {
        if (time > 0) {
            return this.redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
        } else {
            return this.setIfAbsent(key, value);
        }
    }

    /**
     * 递增: +1
     *
     * @param key 键
     * @return long
     */
    public long incr(String key) {
        return this.incr(key, 1);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return long
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减: -1
     *
     * @param key 键
     * @return long
     */
    public long decr(String key) {
        return this.decr(key, 1);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return this.redisTemplate.opsForValue().increment(key, -delta);
    }
    // ================================Hash(哈希)=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public void hmset(String key, Map<String, Object> map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public void hmset(String key, Map<String, Object> map, long time) {
        this.redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            this.expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        return this.redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        boolean result = this.redisTemplate.opsForHash().putIfAbsent(key, item, value);
        if (result && time > 0) {
            this.expire(key, time);
        }
        return result;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        this.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return double
     */
    public double hincr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return double
     */
    public double hdecr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }
    // ============================Set(集合)=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set集合
     */
    public Set<Object> sGet(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        return this.redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return long成功个数
     */
    public long sSet(String key, Object... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return long成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        long count = this.redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return long
     */
    public long sGetSetSize(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return long移除的个数
     */
    public long setRemove(String key, Object... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }
    // ===============================List(列表)=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List集合
     */
    public List<Object> lGet(String key, long start, long end) {
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return long
     */
    public long lGetListSize(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index大于等于0时， 0 表头，1 第二个元素，依次类推；index小于0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    public Object lGetIndex(String key, long index) {
        return this.redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public long lSet(String key, Object value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return boolean
     */
    public long lSet(String key, Object value, long time) {
        long result = this.redisTemplate.opsForList().rightPush(key, value);
        if (result> 0 && time > 0) {
            expire(key, time);
        }
        return result;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public long lSet(String key, List<Object> value) {
        return this.redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return boolean
     */
    public long lSet(String key, List<Object> value, long time) {
        long result = this.redisTemplate.opsForList().rightPushAll(key, value);
        if (result> 0 && time > 0) {
            expire(key, time);
        }
        return result;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     */
    public void lUpdateIndex(String key, long index, Object value) {
        this.redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return boolean移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        return this.redisTemplate.opsForList().remove(key, count, value);
    }
}