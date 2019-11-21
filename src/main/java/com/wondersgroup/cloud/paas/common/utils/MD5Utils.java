package com.wondersgroup.cloud.paas.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Utils {
    public static String encode(String content) {
        String encodeStr = new String(content.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return DigestUtils.md5Hex(encodeStr);
    }

    public static void main(String[] args) {
        String bucketId = "87fa581fadf94e959e9cb377f8032169";
        String timeStamp = String.valueOf(System.currentTimeMillis() + 5 * 60 * 10000);
        String accessKey = "0a2df13Cfd164508BfBc9bc99Cfe51De";
        String secretKey = "ab97b760D62e438Dbf432Dd6E08f4229";
        String headerStr = "[" + accessKey + "|" + bucketId + "|" + timeStamp + "|" + secretKey + "]";
        System.out.println(timeStamp);
        String clientAuthentication = MD5Utils.encode(headerStr);
        System.out.println(clientAuthentication);
    }
}
