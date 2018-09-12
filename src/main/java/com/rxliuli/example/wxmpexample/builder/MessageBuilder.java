package com.rxliuli.example.wxmpexample.builder;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.builder.outxml.BaseBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 消息构建者
 *
 * @author rxliuli
 */
@Component
public class MessageBuilder {
    /**
     * 获得文本消息
     *
     * @param wxMpXmlMessage 微信公众号消息
     * @param content        要回复的文本内容
     * @return 回复的文本消息对象
     */
    public WxMpXmlOutTextMessage buildText(WxMpXmlMessage wxMpXmlMessage, String content) {
        return using(wxMpXmlMessage, WxMpXmlOutMessage.TEXT(), textBuilder -> textBuilder.content(content));
    }

    /**
     * 获取一个图片消息
     *
     * @param wxMpXmlMessage 图片
     * @param mediaId        媒体类型资源的 id
     * @return 微信
     */
    public WxMpXmlOutImageMessage buildImage(WxMpXmlMessage wxMpXmlMessage, String mediaId) {
        return using(wxMpXmlMessage, WxMpXmlOutMessage.IMAGE(), imageBuilder -> imageBuilder.mediaId(mediaId));
    }

    /**
     * 构建一个回复消息对象
     *
     * @param wxMpXmlMessage 微信消息
     * @param t              回复消息构建者对象
     * @param fn             对回复消息的一些额外操作
     * @param <T>            回复消息构建者对象的类型（文本，图片等）
     * @param <R>            构建好的回复消息对象的类型
     * @return 构建好的回复消息对象
     */
    public <T extends BaseBuilder<? extends BaseBuilder<? extends BaseBuilder<? extends BaseBuilder, R>, R>, R>, R extends WxMpXmlOutMessage> R using(WxMpXmlMessage wxMpXmlMessage, T t, Consumer<T> fn) {
        final BaseBuilder<? extends BaseBuilder, R> baseBuilder = t
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser());
        fn.accept(t);
        return baseBuilder
                .build();
    }
}
