package com.wondersgroup.cloud.paas.common.enums;

/**
 * @author chenlong
 * 请求来源
 */
public enum RequestFromEnum {
    /**
     * 第三方
     */
    CLIENT("Client"),
    /**
     * 微服务内部
     */
    INTERNAL("Internal"),
    /**
     * 浏览器
     */
    EXPLORE("Explore");

    private String value;

    RequestFromEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
