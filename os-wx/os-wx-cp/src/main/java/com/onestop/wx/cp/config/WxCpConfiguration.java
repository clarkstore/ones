package com.onestop.wx.cp.config;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Clark
 * @version 2020-08-25
 */
@Configuration
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpConfiguration {
    @Autowired
    private WxCpProperties properties;

    @PostConstruct
    @Bean
    public WxCpService initService() {
        WxCpDefaultConfigImpl configStorage = new WxCpDefaultConfigImpl();
        configStorage.setCorpId(this.properties.getCorpId());
        configStorage.setAgentId(this.properties.getAgentId());
        configStorage.setCorpSecret(this.properties.getSecret());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());

        WxCpService service = new WxCpServiceImpl();
        service.setWxCpConfigStorage(configStorage);
        return service;
    }
}