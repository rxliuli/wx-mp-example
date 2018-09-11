package com.rxliuli.example.wxmpexample.api;

import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信服务窗口 api
 *
 * @author rxliuli
 */
@RestController
@RequestMapping("/wx/portal")
public class WxMpPortalApi {
    private final WxMpService wxMpService;

    @Autowired
    public WxMpPortalApi(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
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
}
