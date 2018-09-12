package com.rxliuli.example.wxmpexample.handler;

import com.rxliuli.example.wxmpexample.builder.MessageBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 基础微信消息处理器
 *
 * @author rxliuli
 */
public abstract class BaseHandler implements WxMpMessageHandler {
    final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MessageBuilder builder;

    /**
     * 默认空实现
     *
     * @param wxMessage      微信的消息
     * @param context        上下文环境(用于在 handler 中传递信息)
     * @param wxMpService    微信 api 服务
     * @param sessionManager 会话管理
     * @return xml 格式的消息, 异步可返回 null
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        return null;
    }
}
