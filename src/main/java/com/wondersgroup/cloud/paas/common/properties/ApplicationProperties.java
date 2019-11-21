package com.wondersgroup.cloud.paas.common.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenlong
 * 通用属性注解
 */
public class ApplicationProperties {

    public static boolean isParamsEncrypt;

    @Value("${application.url.params.encrypt.enable}")
    public void setParamsEncrypt(boolean paramsEncrypt) {
        isParamsEncrypt = paramsEncrypt;
    }

    public static String privateKey;

    @Value("${application.url.params.encrypt.private.key}")
    public void setPrivateKey(String privateKey) {
        ApplicationProperties.privateKey = privateKey;
    }
}
