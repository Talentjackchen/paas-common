package com.wondersgroup.cloud.paas.common.filter;

import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;

import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author chenlong
 */
public class CommonFilter {
    public final String REQUEST_FROM = "request_from";

    public final String ACCESS_TOKEN = "access_token";

    /**
     * 反馈信息
     *
     * @param code     输出代码
     * @param msg      输出内容
     * @param response
     * @throws IOException
     */
    public void responseOut(int code, String msg, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtils.getJsonString(new ResultMap(code, msg)));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
