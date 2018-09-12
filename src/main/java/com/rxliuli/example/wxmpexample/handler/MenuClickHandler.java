package com.rxliuli.example.wxmpexample.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 菜单点击处理器
 *
 * @author rxliuli
 */
@Component
public class MenuClickHandler extends BaseHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        final String content = String.format("消息类型: %s, 事件: %s, 菜单 id: %s",
                wxMessage.getMsgType(),
                wxMessage.getEvent(),
                wxMessage.getEventKey()
        );
        return builder.buildText(wxMessage, content);
    }
}
