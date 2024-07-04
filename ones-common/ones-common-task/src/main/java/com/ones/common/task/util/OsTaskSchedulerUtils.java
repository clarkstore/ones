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

package com.ones.common.task.util;

import cn.hutool.core.map.MapUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务工具类
 *
 * @author Clark
 * @version 2021-04-08
 */
public class OsTaskSchedulerUtils {
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    private static Map<String, ScheduledFuture<?>> scheduledFutureMap = MapUtil.newHashMap();

    @PostConstruct
    public void initialize() {
        //池大小
        this.taskScheduler.setPoolSize(20);
        this.taskScheduler.setThreadNamePrefix("osTask--");
        //调度器shutdown被调用时等待当前被调度的任务完成
        this.taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.taskScheduler.setAwaitTerminationSeconds(60);
        this.taskScheduler.initialize();
    }

    /**
     * 开启任务
     *
     * @param taskNo 任务编号
     * @param task Runnable
     * @param cron cron表达式
     */
    public void startTask(String taskNo, Runnable task, String cron) {
        ScheduledFuture<?> scheduledFuture = this.taskScheduler.schedule(task, new CronTrigger(cron));
        scheduledFutureMap.put(taskNo, scheduledFuture);
    }

    /**
     * 取消任务
     * @param taskNo 任务编号
     */
    public void cancelTask(String taskNo) {
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(taskNo);
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        scheduledFutureMap.remove(taskNo);
    }

    /**
     * 重置任务
     * @param taskNo 任务编号
     * @param task Runnable
     * @param cron cron表达式
     */
    public void resetTask(String taskNo, Runnable task, String cron) {
        this.cancelTask(taskNo);
        this.startTask(taskNo, task, cron);
    }

    /**
     * 重置定时任务，初始化
     */
    public void reset() {
        this.taskScheduler.shutdown();
        this.taskScheduler.initialize();
    }
}
