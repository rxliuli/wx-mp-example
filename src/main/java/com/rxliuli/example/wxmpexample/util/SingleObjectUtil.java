package com.rxliuli.example.wxmpexample.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigInteger;
import java.text.SimpleDateFormat;

/**
 * 全局单一的对象提供工具类
 *
 * @author rxliuli
 */
public interface SingleObjectUtil {
    /**
     * 提供一个全局可用的序列化 Bean
     */
    ObjectMapper OM = new ObjectMapper()
            //Date 对象的格式
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            //默认忽略值为 null 的属性
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            //禁止序列化时间为时间戳
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            //启用序列化 BigDecimal 为非科学计算法格数
            .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
            .registerModules(
                    //注册 Jsr310（Java8 的时间兼容模块）
//                    new JavaTimeModule(),
                    //序列化 Long 为 String
                    new SimpleModule()
                            //大数字直接序列化为 String
                            .addSerializer(Long.class, ToStringSerializer.instance)
                            .addSerializer(Long.TYPE, ToStringSerializer.instance)
                            .addSerializer(long.class, ToStringSerializer.instance)
                            .addSerializer(BigInteger.class, ToStringSerializer.instance)
            )
            //JSON 序列化移除 transient 修饰的 Page 无关紧要的返回属性(Mybatis Plus)
            .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
}
