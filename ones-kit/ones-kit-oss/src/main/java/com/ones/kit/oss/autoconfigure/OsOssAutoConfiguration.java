package com.ones.kit.oss.autoconfigure;

import com.ones.kit.oss.util.OsOssUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Token配置
 *
 * @author Clark
 * @version 2024-07-19
 */
@AutoConfiguration
@ConditionalOnProperty(value = "aizuda.oss.minio.platform")
public class OsOssAutoConfiguration {
    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public OsOssUtils osOssUtils() {
        OsOssUtils utils = new OsOssUtils();
        return utils;
    }
}