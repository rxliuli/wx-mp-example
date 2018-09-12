package com.rxliuli.example.wxmpexample.util.spring;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring 的 servlet 工具
 *
 * @author rxliuli
 */
public class SpringServletUtil {
    /**
     * 获取当前的 http 请求
     *
     * @return 当前的 http 请求对象，可能为 null
     */
    public static HttpServletRequest getCurrentRequest() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }/**
     * 获取当前的 http 响应
     *
     * @return 当前的 http 响应对象，可能为 null
     */
    public static HttpServletResponse getCurrentResponse() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getResponse();
    }
}
