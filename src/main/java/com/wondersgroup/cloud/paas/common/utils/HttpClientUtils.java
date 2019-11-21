package com.wondersgroup.cloud.paas.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author chenlong
 */
public class HttpClientUtils {
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	// 处理GET类请求,参数URL应包含完整的请求参数
	public static String doGet(String url) {
        return doGet(url,null);
    }

	public static String doGet(String url, Map<String, Object> params) {
		return doGet(url,params,null);
	}

    public static String doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(buildURL(url,params));
            HttpGet httpGet = new HttpGet(urlBuilder.toString());

            if(headers!= null && headers.size() > 0){
                for(String key : headers.keySet()){
                    httpGet.setHeader(key, headers.get(key));
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception ex) {
            logger.error("ERROR, call http get" + ex.getMessage(),ex);
        }
        return null;
    }

    /**
     * 处理POST类请求，表格类型
      */
	public static String doPost(String url, Map<String, String> params) {
		return doPost(url,params,null);
	}

    // 处理POST类请求
    public static String doPost(String url, Object object) {
        return doPost(url,object,null);
    }

    /**
     * 处理POST类请求，body类型
     */
    public static String doPost(String url, Object body, Map<String, String> headers) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json; charset=UTF-8");
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(30000)
                    .setConnectTimeout(30000).setSocketTimeout(30000).build();


            if(body == null){
                body = new Object();
            }

            StringEntity strEntity = new StringEntity(JsonUtils.getJsonString(body),
                    Charset.forName("UTF-8"));
            httpPost.setEntity(strEntity);
            httpPost.setConfig(config);

            if(headers != null && headers.size() > 0){
                for(Entry<String,String> entry : headers.entrySet()){
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            logger.error("ERROR, call http post" + e.getMessage(),e);
        }
        return null;
    }


    /**
     * 处理POST类请求，表格类型
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json; charset=UTF-8");
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(60000)
                    .setConnectTimeout(30000).setSocketTimeout(30000).build();

            List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();

            if(params != null && params.size() > 0){
                for (Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
            }

            StringEntity strEntity = new UrlEncodedFormEntity(formParams,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(strEntity);
            httpPost.setConfig(config);

            if(headers != null && headers.size() > 0){
                for(Entry<String,String> entry : headers.entrySet()){
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            logger.error("ERROR, call http post" + e.getMessage(),e);
        }
        return null;
    }

    public static String doPut(String url, Map<String, Object> params) {
        return doPut(url,params,null);
    }

    public static String doPut(String url, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;
        try {

            StringBuilder urlBuilder = new StringBuilder(url);
            int i = 0;
            if(params != null && params.size() > 0){
                for(Entry<String, Object> entry : params.entrySet()){
                    urlBuilder.append((i == 0) ? "?" : "&");
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                    i++;
                }
            }
            HttpPut httpPut = new HttpPut(urlBuilder.toString());
            if(headers != null && headers.size() > 0){
                for(Entry<String, String> entry : headers.entrySet()){
                    httpPut.setHeader(entry.getKey(),entry.getValue());
                }
            }

            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            logger.error("ERROR, call http put" + e.getMessage(),e);
        }
        return null;
    }

    public static String doDelete(String url) {
        return doDelete(url,null);
    }

    public static String doDelete(String url, Map<String, Object> params) {
        return doDelete(url,params,null);
    }

    public static String doDelete(String url, Map<String, Object> params, Map<String, String> headers) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            StringBuilder urlBuilder = new StringBuilder(url);
            int i = 0;
            if(params != null && params.size() > 0){
                for (String key : params.keySet()) {
                    urlBuilder.append((i == 0) ? "?" : "&");
                    urlBuilder.append(key).append("=").append(params.get(key));
                    i++;
                }
            }
            HttpDelete httpDelete = new HttpDelete(urlBuilder.toString());

            if(headers != null && headers.size() > 0){
                for(String key : headers.keySet()){
                    httpDelete.setHeader(key, headers.get(key));
                }
            }

            CloseableHttpResponse response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            logger.error("ERROR, call http get" + e.getMessage(),e);
        }
        return null;
    }

    /**
     * 根据有序参数生成URL
     * @param url
     * @param params
     * @return
     */
    public static String generateURL(String url, LinkedHashMap<String,Object> params){
        return  buildURL(url,params);
    }

    private static String buildURL(String url, Map<String,Object> params){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(url);
        try{
            int i = 0;
            if(params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    strBuilder.append((i == 0) ? "?" : "&");
                    strBuilder.append(key).append("=");
                    if (params.get(key) != null) {
                        strBuilder.append(URLEncoder.encode(String.valueOf(params.get(key)), "utf-8"));
                        i++;
                    }
                }
            }
        }catch (Exception ex){
            logger.error("构建URL失败",ex);
        }

        return  strBuilder.toString();
    }
}
