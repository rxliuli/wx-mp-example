package com.rxliuli.example.wxmpexample.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * 用户发送消息的处理器
 *
 * @author rxliuli
 */
@Component
public class MsgHandler extends BaseHandler {
    /**
     * 发送这条消息就返回给用户图片的内容
     */
    private static final String getImgMsg = "获得图片";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        log.info("接收到消息: {}", wxMessage.getMsg());
        if (!getImgMsg.equals(wxMessage.getContent())) {
            final String content = "您发送的消息为: " + wxMessage.getContent();
            return builder.buildText(wxMessage, content);
        }
        return replyMedia(wxMessage, wxMpService);
    }

    /**
     * 返回给用户一个图片
     *
     * @param wxMessage   微信消息
     * @param wxMpService 微信 api 服务
     * @return 微信回复消息
     */
    private WxMpXmlOutMessage replyMedia(WxMpXmlMessage wxMessage, WxMpService wxMpService) {
        try {
            final WxMediaUploadResult result = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, new File("res/img/test-img.jpg"));
            return builder.buildImage(wxMessage, result.getMediaId());
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
