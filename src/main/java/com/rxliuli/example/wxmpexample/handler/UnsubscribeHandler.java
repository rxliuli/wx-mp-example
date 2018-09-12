package com.rxliuli.example.wxmpexample.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户取消关注的处理器
 *
 * @author rxliuli
 */
@Component
public class UnsubscribeHandler extends BaseHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        log.info("用户 {} 取消关注了公众号", wxMessage.getFromUser());
        return null;
    }
}
