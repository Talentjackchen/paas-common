package com.wondersgroup.cloud.paas.common.filter;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.properties.ApplicationProperties;
import com.wondersgroup.cloud.paas.common.utils.AesUtils;
import com.wondersgroup.cloud.paas.common.utils.ExceptionUtils;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.common.utils.RsaUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.*;

/**
 * @author chenlong
 */
public class SensitiveDataFilter extends CommonFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public final String KEYWORD = "keyword";

    public final String IV = "iv";

    public final String FILE_SENSITIVE = "fileSensitive";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (ApplicationProperties.isParamsEncrypt) {
            /* 秘钥 */
            String privateKey = ((HttpServletRequest) request).getHeader(KEYWORD);
            String iv = ((HttpServletRequest) request).getHeader(IV);
            Map<String, String[]> originParamMap = request.getParameterMap();
            Map<String, String[]> paramMap = new HashMap<>();
            /* 使用浏览器下载无法增加头部信息，加密的信息以fileSensitive参数放在URL后面，格式：keyword|iv */
            if (StringUtils.isBlank(privateKey) && originParamMap != null && originParamMap.size() > 0) {
                for (String paramKey : originParamMap.keySet()) {
                    if (FILE_SENSITIVE.equals(paramKey)) {
                        String[] fileSensitiveStr = originParamMap.get(paramKey);
                        if (fileSensitiveStr != null && fileSensitiveStr.length > 0) {
                            String[] fileSensitives = fileSensitiveStr[0].split("\\|\\|\\|");
                            privateKey = fileSensitives[0];
                            iv = fileSensitives[1];
                        }
                    }
                    paramMap.put(paramKey, originParamMap.get(paramKey));
                }
                paramMap.remove(FILE_SENSITIVE);
                paramMap.remove(REQUEST_FROM);
            } else {
                paramMap = originParamMap;
            }

            if (StringUtils.isNotEmpty(privateKey)) {
                SensitiveDataHttpServletRequest requestWrapper = new SensitiveDataHttpServletRequest((HttpServletRequest) request);
                try {
                    privateKey = RsaUtils.decrypt(privateKey, ApplicationProperties.privateKey);
                } catch (Exception ex) {
                    logger.error(CommonConstant.EXCEPTION_MSG_PRIVATE_KEY_DECRYPT, ExceptionUtils.getExceptionStackTrace(ex, 4000));
                    responseOut(CommonConstant.EXCEPTION_CODE_PRIVATE_KEY_DECRYPT, CommonConstant.EXCEPTION_MSG_PRIVATE_KEY_DECRYPT, response);
                    return;
                }

                String tempKey = "";
                String tempValue = "";
                try {
                    /* 两种模式取值 */
                    if (paramMap != null && paramMap.size() > 0) {
                        for (String key : paramMap.keySet()) {
                            tempKey = key;
                            String[] values = paramMap.get(key);
                            /* access_token参数不参与解密 */
                            if (!ACCESS_TOKEN.equals(key)) {
                                for (int i = 0; i < values.length; i++) {
                                    tempValue = values[i];
                                    String value = AesUtils.decrypt(values[i], privateKey, iv);
                                    values[i] = value;
                                }
                            }
                            requestWrapper.setRequestParameter(key, values);
                        }
                    } else {
                        String requestBody = getRequestBody((HttpServletRequest) request);
                        String jsonFlag = ((HttpServletRequest) request).getHeader("JsonFlag");
                        if ("true".equals(jsonFlag)) {
                            String data = AesUtils.decrypt(requestBody, privateKey, iv);
                            requestWrapper.setRequestBody(data);
                        } else {
                            Map<String, Object> bodyMap = (Map<String, Object>) JsonUtils.jsonStringToBean(requestBody, Map.class);
                            if (bodyMap != null && bodyMap.size() > 0) {
                                for (String key : bodyMap.keySet()) {
                                    Object value = bodyMap.get(key);
                                    if (value instanceof List) {
                                        List<String> list = new ArrayList<>();
                                        List<String> values = (List) value;
                                        for (String data : values) {
                                            data = AesUtils.decrypt(data, privateKey, iv);
                                            list.add(data);
                                        }
                                        bodyMap.put(key, list);
                                    } else {
                                        String data = AesUtils.decrypt(value.toString(), privateKey, iv);
                                        bodyMap.put(key, data);
                                    }
                                }
                                requestWrapper.setRequestBody(JsonUtils.getJsonString(bodyMap));
                            }
                        }
                    }
                } catch (Exception ex) {
                    String errorMess = CommonConstant.EXCEPTION_MSG_DATA_KEY_DECRYPT;
                    if (!"".equals(tempKey)) {
                        errorMess += ",key:" + tempKey + " value:" + tempValue;
                    }

                    logger.error(errorMess, ExceptionUtils.getExceptionStackTrace(ex, 4000));
                    responseOut(CommonConstant.EXCEPTION_CODE_DATA_KEY_DECRYPT, CommonConstant.EXCEPTION_MSG_DATA_KEY_DECRYPT, response);
                    return;
                }
                chain.doFilter(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 读取Body数据
     *
     * @param request
     * @return
     */
    private String getRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = "";
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
            requestBody = strBuilder.toString();
        } catch (IOException e) {
            logger.error("读取请求Body体内容失败！");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return requestBody;
    }

    class SensitiveDataHttpServletRequest extends HttpServletRequestWrapper {
        private String requestBody;
        private Map<String, String[]> paramMap = new HashMap<String, String[]>();

        public SensitiveDataHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public String getParameter(String name) {
            String[] array = paramMap.get(name);
            if (array != null && array.length > 0) {
                return array[0];
            } else {
                return null;
            }
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(paramMap);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(paramMap.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return paramMap.get(name);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStream() {
                private InputStream in = new ByteArrayInputStream(requestBody.getBytes(getRequest().getCharacterEncoding()));

                @Override
                public int read() throws IOException {
                    return in.read();
                }

                @Override
                public boolean isFinished() {
                    try {
                        return in.available() == 0;
                    } catch (Exception ex) {
                        return false;
                    }
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readlistener) {
                }
            };
        }

        public void setRequestParameter(String name, Object value) {
            if (value != null) {
                if (value instanceof String[]) {
                    paramMap.put(name, (String[]) value);
                } else if (value instanceof String) {
                    paramMap.put(name, new String[]{(String) value});
                } else {
                    paramMap.put(name, new String[]{String.valueOf(value)});
                }
            }
        }

        public void setRequestBody(String requestBody) {
            this.requestBody = requestBody;
        }
    }
}