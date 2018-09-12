package com.rxliuli.example.wxmpexample.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户关注的处理器
 *
 * @author rxliuli
 */
@Component
public class SubscribeHandler extends BaseHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String content = "感谢你关注了公众号 ";
        try {
            content += wxMpService.getUserService().userInfo(wxMessage.getFromUser()).getNickname();
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
        }
        return builder.buildText(wxMessage, content);
    }
}
