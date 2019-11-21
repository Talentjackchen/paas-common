package com.wondersgroup.cloud.paas.common.utils;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author chenlong
 */
public class CommonUtils {

    public static final String ENCODING_UTF8 = "utf-8";

    public static final String ENCODING_ASCII = "ascii";

    public final static String NULL_STR = "";

	/**
	 * 生成随机数
	 * @return
	 */
	public static String generateId() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		id = id.replaceAll("-", "");
		return id;
	}

    /**
     * 生成带有大写字母的UUID
     * @return
     */
	public static String generateCapitalUUID(){
        String uuid = generateId();
        //替换  1 7 13 19 25位置上的为大写字母，如果不是字母，则跳过
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        for(int i=1;i<6;i++){
            //第一个位置上是否是字母
            int k = i*6;
            char c = uuid.charAt(k);
            boolean matches ;
            while (true){
                matches = pattern.matcher(String.valueOf(c)).matches();
                char c1 = uuid.substring(k, k + 1).charAt(0);
                if (matches){
                    uuid = uuid.substring(0, k) + uuid.substring(k, k + 1).toUpperCase() + uuid.substring(k+1,32);
                }
                if(matches&&k<31){
                    c = uuid.charAt(++k);
                }
                else if(k<32){
                    uuid = uuid.substring(0, k) + uuid.substring(k, k + 1).toUpperCase() + uuid.substring(k+1,32);
                    break;
                }else {
                    return uuid;
                }
            }
        }
	    return uuid;
    }







}
