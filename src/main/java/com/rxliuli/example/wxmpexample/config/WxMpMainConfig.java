package com.rxliuli.example.wxmpexample.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 微信公众号主要的配置类
 *
 * @author rxliuli
 */
@Configuration
@EnableConfigurationProperties(WxMpPropertiesConfig.class)
public class WxMpMainConfig {
    private final WxMpPropertiesConfig wxMpPropertiesConfig;

    /**
     * 微信公众号的服务对象
     * 用户调用微信的各种 API, 例如获取 access_token
     */
    private WxMpService wxMpService;

    @Autowired
    public WxMpMainConfig(WxMpPropertiesConfig wxMpPropertiesConfig) {
        this.wxMpPropertiesConfig = wxMpPropertiesConfig;
    }

    /**
     * 初始化路由列表和微信服务 api 对象
     */
    @PostConstruct
    public void init() {
        //配置微信 api 对象的策略（目前在内存中）
        final WxMpInMemoryConfigStorage storage = new WxMpInMemoryConfigStorage();
        storage.setAppId(wxMpPropertiesConfig.getAppId());
        storage.setSecret(wxMpPropertiesConfig.getSecret());
        storage.setAesKey(wxMpPropertiesConfig.getAesKey());
        storage.setToken(wxMpPropertiesConfig.getToken());
        //设置策略到服务对象中
        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(storage);
    }

    @Bean
    public WxMpService wxMpService() {
        return wxMpService;
    }
}
