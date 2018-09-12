package com.rxliuli.example.wxmpexample.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json 工具类
 *
 * @author rxliuli
 */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 转换对象为字符串
     *
     * @param object 对象
     * @return 转换字符串
     */
    public static <T> String toJson(T object) {
        try {
            return SingleObjectUtil.OM.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("序列化失败 {}", e);
            return null;
        }
    }

    /**
     * 反序列化
     *
     * @param json  反序列化的字符串
     * @param clazz 反序列化类型对象
     * @param <T>   反序列化类型
     * @return 反序列化后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return SingleObjectUtil.OM.readValue(json, clazz);
        } catch (IOException e) {
            log.error("反序列化失败 {}", e);
            return null;
        }
    }

    /**
     * 反序列化
     *
     * @param json          反序列化的字符串
     * @param typeReference 反序列化的封装对象(用于反序列化复杂的对象)
     * @param <T>           反序列化类型
     * @return 反序列化后的对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return SingleObjectUtil.OM.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("反序列化失败 {}", e);
            return null;
        }
    }

}
