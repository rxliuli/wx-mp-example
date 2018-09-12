package com.rxliuli.example.wxmpexample.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志处理器
 *
 * @author rxliuli
 */
@Component
public class LogHandler extends BaseHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        try {
            log.info("接收到了请求：{}", new ObjectMapper().writeValueAsString(wxMessage));
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }
}
