/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HttpClientUtil
 * Author:   LiYuAn
 * Date:     2018/5/17 16:20
 * Description: httpclient
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.worker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈httpclient〉
 *
 * @author LiYuAn
 * @create 2018/5/17
 * @since 1.0.0
 */
public class HttpClientUtil {


    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 3000;
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(apiUrl);
        httpPost.setConfig(requestConfig);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            log.warn("执行状态码 : " + statusCode);
        } catch (Exception e) {
            log.warn("请求异常", e);
            try {
                httpClient.close();
            } catch (IOException e1) {
                log.warn("请求异常", e1);
                return "{msg:'失败'}";
            }
            return "{msg:'失败'}";
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                log.warn("请求异常", e);
                return "{msg:'失败'}";
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        httpPost.setConfig(requestConfig);
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, HTTP.UTF_8));
            response = httpClient.execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            log.warn("执行状态码 : " + statusCode);

        } catch (Exception e) {
            log.warn("请求异常", e);
            try {
                httpClient.close();
            } catch (IOException e1) {
                log.warn("请求异常", e1);
                return "{msg:'失败'}";
            }
            return "{msg:'失败'}";
        }
        HttpEntity entity = response.getEntity();
        try {
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.warn("请求异常", e);
            return "{msg:'失败'}";
        }

        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static String doPost(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        httpPost.setConfig(requestConfig);
        StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json;charset=utf-8");
        httpPost.setEntity(stringEntity);

        try {
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            log.warn("请求异常", e);
            try {
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                return "{msg:'失败'}";
            }
            return "{msg:'失败'}";
        }

        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            return "{msg:'失败'}";
        }
        try {
            httpStr = EntityUtils.toString(entity, "UTF-8");
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "{msg:'失败'}";
        }


        return httpStr;
    }


}
