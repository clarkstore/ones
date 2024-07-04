package com.ones.kit.oss.autoconfigure;

import com.ones.kit.oss.util.OsOssUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Token配置
 *
 * @author Clark
 * @version 2022-05-26
 */
@AutoConfiguration
public class OsOssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OsOssUtils osOssUtils() {
        OsOssUtils utils = new OsOssUtils();
        return utils;
    }
}