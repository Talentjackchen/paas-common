package com.wondersgroup.cloud.paas.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 操作字符串的工具类
 */
public final class ExceptionUtils {
    private static Logger log = LoggerFactory.getLogger(ExceptionUtils.class);

    /**
     * 获取异常栈信息
     *
     * @param e Throwable
     * @param maxByteSize 最大
     * @return result
     */
    public static String getExceptionStackTrace(Throwable e, Integer maxByteSize) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            String result = sw.getBuffer().toString();
            if (maxByteSize != null) {
                result = limitStrOfBytes(result, maxByteSize);
            }
            return result;
        } finally {
            try {
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException e1) {
                log.warn(e1.getMessage());
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 按照字节码长度来截取字符串
     *
     * @param text        原始字符串
     * @param maxDataSize 最大字符串长度
     * @return 截取字符串
     */
    public static String limitStrOfBytes(String text, int maxDataSize) {
        try {
            byte[] bytes = (text == null ? "" : text).getBytes("utf-8");
            if (bytes.length > maxDataSize) {
                byte[] newBytes = Arrays.copyOf(bytes, maxDataSize);
                return new String(newBytes, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

}
