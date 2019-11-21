package com.wondersgroup.cloud.paas.common.utils;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private final static SimpleDateFormat DEFAULT_FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将对象转换成JSON字符串
     *
     * @param bean 可以为基本的object 或者为 数组
     * @return JSON字符串
     */
    public static String getJsonString(Object bean) {
        return getJsonString(bean,DEFAULT_FORMAT);
    }

    /**
     * 将对象转换成JSON字符串
     *
     * @param bean 可以为基本的object 或者为 数组
     * @return JSON字符串
     */
    public static String getJsonString(Object bean, DateFormat dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(dateFormat);
        try {
            return mapper.writeValueAsString(bean);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将json格式的字符串的转化为Bean对象
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T jsonStringToBean(String jsonStr, Class<T> clazz, DateFormat dateFormat) {
        if(isEmpty(jsonStr)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setDateFormat(dateFormat);
            return mapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json格式的字符串的转化为Bean对象
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T jsonStringToBean(String jsonStr, Class<T> clazz) {
        return jsonStringToBean(jsonStr, clazz,DEFAULT_FORMAT);
    }

    /**
     * 将json格式的字符串（数组形式）的转化为List对象
     * @param <T>
     * @param jsonArrStr
     * @param clazz	List中对象类型
     * @return
     */
    public static <T> List<T> jsonStringToList(String jsonArrStr, Class<T> clazz) {
        return jsonStringToList(jsonArrStr, clazz, DEFAULT_FORMAT);
    }

    /**
     * 将json格式的字符串（数组形式）的转化为List对象
     * @param <T>
     * @param jsonArrStr
     * @param clazz List中对象类型
     * @return
     */
    public static <T> List<T> jsonStringToList(String jsonArrStr, Class<T> clazz, DateFormat dateFormat) {
        if(isEmpty(jsonArrStr)) {
            return new ArrayList<T>();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(dateFormat);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonArrStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }


    public static <T> ResultMap<T> jsonStringToResultMap(String jsonArrStr, Class<T> clazz) {
        if(isEmpty(jsonArrStr)) {
            return new ResultMap<T>();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(DEFAULT_FORMAT);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ResultMap.class, clazz);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonArrStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json转map
     *
     * @param jsonStr
     * @return
     */
    public static Map jsonToMap(String jsonStr) {
        return (Map) JSON.parse(jsonStr.toString());
    }

    private static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

}
