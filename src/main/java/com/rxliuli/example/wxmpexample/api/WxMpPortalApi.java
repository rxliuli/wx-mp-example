package com.rxliuli.example.wxmpexample.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务窗口 api
 *
 * @author rxliuli
 */
@RestController
@RequestMapping("/wx/portal")
public class WxMpPortalApi {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WxMpService wxMpService;
    private final WxMpMessageRouter router;

    @Autowired
    public WxMpPortalApi(WxMpService wxMpService, WxMpMessageRouter router) {
        this.wxMpService = wxMpService;
        this.router = router;
    }

    /**
     * 微信认证当前服务可用
     *
     * @param signature 微信加密签名，signature 结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce 参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   成功后回传的随机字符串
     * @return {@code echostr}
     */
    @GetMapping
    public String authGet(
            String signature,
            String timestamp,
            String nonce,
            String echostr
    ) {
        if (StringUtils.isAnyEmpty(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求非法参数!");
        }
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * 对所有来自微信服务器的消息进行预处理
     *
     * @param requestBody 请求体（xml 格式）
     * @return 明文消息
     */
    @PostMapping
    public String authPost(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature
    ) throws JsonProcessingException {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求, 并非微信发来的");
        }

        WxMpXmlMessage inMessage = null;
        if (encType == null) {
            //明文传输
            inMessage = WxMpXmlMessage.fromXml(requestBody);
        } else if ("aes".equals(encType)) {
            //aes 加密
            inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
        }
        WxMpXmlOutMessage outMessage = router.route(inMessage);
        log.info("客户端发送的消息: {}", new ObjectMapper().writeValueAsString(outMessage));
        return outMessage == null ? "" : outMessage.toXml();
    }
}
