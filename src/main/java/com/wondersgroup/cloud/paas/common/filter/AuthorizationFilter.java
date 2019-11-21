package com.wondersgroup.cloud.paas.common.filter;

import com.wondersgroup.cloud.paas.common.constant.AuthConstant;
import com.wondersgroup.cloud.paas.common.pojo.TokenPojo;
import com.wondersgroup.cloud.paas.common.utils.UserUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenlong
 * 权限认证过滤器
 */
public class AuthorizationFilter extends CommonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig){}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        boolean flag = false;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = request.getHeader(AuthConstant.AUTHORIZATION);
        /* 文件下载Token值是作为URL参数access_token传递 */
        if(StringUtils.isBlank(token)){
            token = request.getParameter(ACCESS_TOKEN);
            token = AuthConstant.BEARER + token;
        }

        /* 认证Token值 */
        if(StringUtils.isNotBlank(token)){
            token = token.substring(AuthConstant.BEARER.length());
            TokenPojo tokenPojo = UserUtils.checkToken(token);
            flag = tokenPojo.isSuccess();
        }

        if(flag){
            chain.doFilter(servletRequest,servletResponse);
        }else{
            responseOut(AuthConstant.AUTHORIZATION_FAIL_CODE,AuthConstant.AUTHORIZATION_FAIL_MSG,servletResponse);
        }
    }

    @Override
    public void destroy() {}
}
