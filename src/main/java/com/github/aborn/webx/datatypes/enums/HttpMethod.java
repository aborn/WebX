package com.github.aborn.webx.datatypes.enums;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aborn
 * @date 2021/02/02 11:19 AM
 */
public enum HttpMethod {
    // Http方法
    GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE, CONNECT;

    private static final Map<String, HttpMethod> methodMap = new HashMap(8);

    public static HttpMethod getMethod(String method) {
        if (StringUtils.isBlank(method)) {
            return null;
        }

        String[] split = method.split("\\.");

        if (split.length > 1) {
            method = split[split.length - 1].toUpperCase();
            return HttpMethod.valueOf(method);
        } else {
            return HttpMethod.valueOf(method.toUpperCase());
        }
    }

    static {
        for (HttpMethod httpMethod : values()) {
            methodMap.put(httpMethod.name(), httpMethod);
        }
    }
}
