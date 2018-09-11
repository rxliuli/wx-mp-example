package com.rxliuli.example.wxmpexample.api;

import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 微信公众号 api 基类
 *
 * @author rxliuli
 */
public class WxMpBaseApi {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected WxMpService wxMpService;
}
