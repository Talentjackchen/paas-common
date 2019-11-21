package com.wondersgroup.cloud.paas.common.utils;

public class QueryUtils {

    public static String generateLikeString(String string){
        return "%"+string+"%";
    }

    public static String generateLikeStringFromEscape(String content) {
        String regex = "%|_";
        content = content.replaceAll(regex, "");
        return generateLikeString(content);
    }
}
