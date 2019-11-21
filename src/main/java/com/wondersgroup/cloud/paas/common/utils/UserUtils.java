package com.wondersgroup.cloud.paas.common.utils;

import com.wondersgroup.cloud.paas.common.constant.AuthConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.pojo.TokenPojo;
import com.wondersgroup.cloud.paas.common.pojo.UserVo;
import com.wondersgroup.cloud.paas.common.theadlocal.AuthorizationThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {
    public static String accountServerUrl;

    @Value("${auth.server.url}")
    public void setAccountServerUrl(String accountServerUrl) {
        UserUtils.accountServerUrl = accountServerUrl;
    }

    public static ResultMap getLoginUser(String authentication) {
        Map<String, String> headers = new HashMap<>();
        if(!authentication.startsWith(AuthConstant.BEARER) && !authentication.startsWith(AuthConstant.LETTER_BEARER)){
            headers.put(AuthConstant.AUTHORIZATION,AuthConstant.BEARER + authentication);
        }else{
            headers.put(AuthConstant.AUTHORIZATION,authentication);
        }
        String url = accountServerUrl + "/user/current";
        String jsonString = HttpClientUtils.doGet(url,null,headers);
        if(StringUtils.isNotBlank(jsonString)){
            return JsonUtils.jsonStringToBean(jsonString, ResultMap.class);
        }else{
            return null;
        }

    }

    public static String getAuthToken(){
        String token = (String) AuthorizationThreadLocal.getCurrent().get();
        if (StringUtils.isBlank(token)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                token = request.getHeader(AuthConstant.AUTHORIZATION);
            }
        }
        return token;
    }

    public static UserVo getUserInfo(){
        String token = getAuthToken();
        ResultMap resultMap = getLoginUser(token);
        if(resultMap == null ){
            return null;
        }
        String jsonString = JsonUtils.getJsonString(resultMap.getResult());
        return JsonUtils.jsonStringToBean(jsonString, UserVo.class);
    }

    public static TokenPojo checkToken(String token){
        Map<String, Object> params = new HashMap<>();
        params.put("token",token);
        String url = accountServerUrl + "/oauth/check_token";
        String jsonString = HttpClientUtils.doGet(url,params);
        TokenPojo tokenPojo;
        if(StringUtils.isNotBlank(jsonString)){
            tokenPojo = JsonUtils.jsonStringToBean(jsonString, TokenPojo.class);
            if("invalid_token".equals(tokenPojo.getError())){
                tokenPojo.setSuccess(false);
            }else{
                tokenPojo.setSuccess(true);
            }
        }else{
            tokenPojo = new TokenPojo();
            tokenPojo.setSuccess(false);
        }
        return tokenPojo;
    }
}
