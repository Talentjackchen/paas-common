package com.wondersgroup.cloud.paas.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * describe : 集合工具类
 * create_by : zhangyongzhao
 * create_time : 2019/5/16
 */
public class CollectionUtils {

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

}
