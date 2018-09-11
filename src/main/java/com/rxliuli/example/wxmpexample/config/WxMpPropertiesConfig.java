package com.rxliuli.example.wxmpexample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信公众号属性配置
 *
 * @author rxliuli
 */
@ConfigurationProperties(prefix = "custom.wx.mp")
public class WxMpPropertiesConfig {
    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    public String getAppId() {
        return appId;
    }

    public WxMpPropertiesConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public WxMpPropertiesConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getToken() {
        return token;
    }

    public WxMpPropertiesConfig setToken(String token) {
        this.token = token;
        return this;
    }

    public String getAesKey() {
        return aesKey;
    }

    public WxMpPropertiesConfig setAesKey(String aesKey) {
        this.aesKey = aesKey;
        return this;
    }
}
