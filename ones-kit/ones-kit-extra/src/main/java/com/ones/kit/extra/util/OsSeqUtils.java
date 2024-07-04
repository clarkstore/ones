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

package com.ones.kit.extra.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 流水号工具类
 *
 * @author Clark
 * @version 2023-02-16
 */
@Slf4j
public class OsSeqUtils {
    /**
     * 记录已有流水单
     * 允许设置初始值
     */
    @Setter
    private long seqNo = 1;
    /**
     * 配置参数
     */
    private Map<String, String> map = MapUtil.newConcurrentHashMap();
    /**
     * map记录当前日期seq最大值key
     */
    private static final String MAP_SEQ_KEY = "date";
    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();
    /**
     * redis缓存24小时过期
     */
//    private static final long REDIS_DURATION_SECONDS = 60 * 60 * 24;
    /**
     * redis分布式锁key
     */
//    private static final String REDIS_LOCK_KEY = "seqLock:";
    /**
     * 配置缓存可以根据不同业务类型分别创建序列号
     */
    @Autowired(required = false)
//    private OsRedisUtils redisUtils;
//    @Autowired(required = false)
//    private OsRedissonUtils redissonUtils;
    private OsSeqConfig seqConfig;
    public OsSeqUtils(OsSeqConfig seqConfig) {
        this.seqConfig = seqConfig;
    }

    /**
     * 取流水号
     * @return
     */
    public String getSeqNo() {
//        // 使用redis
//        if (this.seqConfig.isUseRedis()) {
//            return this.getSeqByRedis(StrUtil.EMPTY);
//        }
        return this.getSeq(StrUtil.EMPTY);
    }

    /**
     * 取流水号
     * @return
     */
    public String getSeqNo(String bizFlag) {
//        // 使用redis
//        if (this.seqConfig.isUseRedis()) {
//            return this.getSeqByRedis(bizFlag);
//        }
        return this.getSeq(bizFlag);
    }

    /**
     * 根据业务标识取流水号
     * @param bizFlag
     * @return
     */
//    private String getSeqByRedis(String bizFlag) {
//        long seq = 1;
//        String todayStr = DateUtil.format(LocalDateTime.now(), this.seqConfig.getDateFormat());
//        this.map.put("bizFlag", bizFlag);
//        this.map.put("date", todayStr);
//
//        String redisKey = StrUtil.format(OsSeqConfig.RedisKeyTemplate, this.map);
//        String lockKey = REDIS_LOCK_KEY + bizFlag;
//
//        try {
//            boolean isGet;
//            RLock lock = this.redissonUtils.fairLock(lockKey);
//            do {
//                //加锁
//                isGet = this.redissonUtils.tryLock(lock, TimeUnit.MILLISECONDS, 1, 5000);
//                if (isGet) {
//                    if (this.redisUtils.hasKey(redisKey)) {
//                        seq = this.redisUtils.incr(redisKey);
//                        log.error("----------seq---------" + seq);
//                    } else {
//                        // 缓存24小时
//                        this.redisUtils.set(redisKey, seq, REDIS_DURATION_SECONDS);
//                    }
//                } else {
//                    ThreadUtil.sleep(40);
//                }
//            } while (!isGet);
//        } finally {
//            //释放锁
//            this.redissonUtils.unlock(lockKey);
//        }
//
//        String seqStr = StrUtil.fillBefore(String.valueOf(seq), '0', this.seqConfig.getSeqLength());
//        map.put("seq", seqStr);
//
//        return StrUtil.format(this.seqConfig.getTemplate(), this.map, false);
//    }

    /**
     * 取得流水号
     * 流水号不因bizFlag不同而单独累计
     * @param bizFlag
     * @return
     */
    private String getSeq(String bizFlag) {
        String todayStr = DateUtil.format(LocalDateTime.now(), this.seqConfig.getDateFormat());
        //上锁
        REENTRANT_LOCK.lock();
        try {
            if (todayStr.equals(this.map.get(MAP_SEQ_KEY))) {
                //当日流水号+1
                this.seqNo += 1;
            } else {
                //重置流水号
                this.seqNo = 1;
            }
            // 保存当前日期，判断是否有新起始一天
            this.map.put(MAP_SEQ_KEY, todayStr);

            Map<String, String> param = MapUtil.newHashMap();
            param.put("bizFlag", bizFlag);
            param.put("date", todayStr);

            String seqStr = StrUtil.fillBefore(String.valueOf(this.seqNo), '0', this.seqConfig.getSeqLength());
            param.put("seq", seqStr);

            return StrUtil.format(this.seqConfig.getTemplate(), param, false);
        } finally {
            //释放锁
            if (REENTRANT_LOCK.isLocked()) {
                REENTRANT_LOCK.unlock();
            }
        }
    }

    /**
     * 流水号配置类
     */
    @Setter
    @Getter
    @ToString
    public static class OsSeqConfig {
        /**
         * 序号缓存key
         */
        public static final String RedisKeyTemplate = "os-{date}{bizFlag}";
        /**
         * 序号规则模板 {bizFlag}{date}{seq}三元素组合，默认值{bizFlag}{date}{seq}
         * bizFlag业务部分标识：序号规则模板中做前缀可以为字母和数字，中间建议为数字，通过传入bizFlag参数取得不同业务模块序号
         */
        private String template = "{bizFlag}{date}{seq}";
        /**
         * 日期部分格式：yyyyMMdd/yyMMdd/yyyyMMddHHmmss，可以缺省此项配置，默认值：yyyyMMdd
         */
        private String dateFormat = "yyyyMMdd";
        /**
         * 序号部分长度
         */
        private int seqLength;
        /**
         * 使用redis
         */
        private boolean useRedis;
    }
}
