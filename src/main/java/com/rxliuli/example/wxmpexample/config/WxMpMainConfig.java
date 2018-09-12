package com.rxliuli.example.wxmpexample.config;

import com.rxliuli.example.wxmpexample.handler.*;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static me.chanjar.weixin.common.api.WxConsts.*;

/**
 * 微信公众号主要的配置类
 *
 * @author rxliuli
 */
@Configuration
@EnableConfigurationProperties(WxMpPropertiesConfig.class)
public class WxMpMainConfig {
    private final WxMpPropertiesConfig wxMpPropertiesConfig;
    private final MsgHandler msgHandler;
    private final LogHandler logHandler;
    private final MenuClickHandler menuClickHandler;
    private final SubscribeHandler subscribeHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final MenuViewHandler menuViewHandler;
    /**
     * 微信公众号监听管理路由映射表
     * 其实就是监听用户在公众号的操作罢了, 比如点击了某个菜单, 发送了一些消息
     */
    private WxMpMessageRouter wxMpMessageRouter;
    /**
     * 微信公众号的服务对象
     * 用户调用微信的各种 API, 例如获取 access_token
     */
    private WxMpService wxMpService;

    @Autowired
    public WxMpMainConfig(WxMpPropertiesConfig wxMpPropertiesConfig, MsgHandler msgHandler, LogHandler logHandler, MenuClickHandler menuClickHandler, SubscribeHandler subscribeHandler, UnsubscribeHandler unsubscribeHandler, MenuViewHandler menuViewHandler) {
        this.wxMpPropertiesConfig = wxMpPropertiesConfig;
        this.msgHandler = msgHandler;
        this.logHandler = logHandler;
        this.menuClickHandler = menuClickHandler;
        this.subscribeHandler = subscribeHandler;
        this.unsubscribeHandler = unsubscribeHandler;
        this.menuViewHandler = menuViewHandler;
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
        //添加路由
        wxMpMessageRouter = this.newRouter(wxMpService);
    }

    /**
     * 根据微信 api 服务对象创建一个微信监听路由
     *
     * @param wxMpService 微信 api 服务
     * @return 微信监听路由对象
     */
    private WxMpMessageRouter newRouter(WxMpService wxMpService) {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        //日志记录
        router.rule().handler(this.logHandler).next();
        //菜单点击消息
        router.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.CLICK)
                .handler(this.menuClickHandler).end();
        //菜单跳转链接消息
        router.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.VIEW).handler(this.menuViewHandler).end();
        //关注
        router.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SUBSCRIBE)
                .handler(this.subscribeHandler).end();
        //取消关注
        router.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.UNSUBSCRIBE)
                .handler(this.unsubscribeHandler).end();
        //发送消息(默认)
        router.rule().async(false).handler(this.msgHandler).end();
        return router;
    }

    @Bean
    public WxMpService wxMpService() {
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter() {
        return wxMpMessageRouter;
    }
}
