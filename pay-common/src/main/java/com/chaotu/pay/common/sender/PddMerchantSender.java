package com.chaotu.pay.common.sender;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

@Slf4j
public class PddMerchantSender<T> implements Sender<T> {

    public PddMerchantSender(String url, Object param, String cookie){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        //post.setHeader("Cookie", cookie);
        post.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; SM-G892A Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/67.0.3396.87 Mobile Safari/537.36");
        String str = null;
        String contentType = null;
        if(param instanceof String){
            contentType="application/x-www-form-urlencoded";
            post.setHeader("Content-Type", contentType);
            str = param.toString();
        }else{
            contentType="application/json";
            post.setHeader("Content-Type", contentType);
            str = JSON.toJSONString(param);
        }
        StringEntity entity = new StringEntity( str, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType(contentType);
        // 发送Json格式的数据请求
        post.setEntity(entity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).build();
        post.setConfig(requestConfig);
        this.post = post;
        this.client = client;
    }
    private HttpPost post;
    private HttpClient client;
    @Override
    public T send() {
        try{
            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                log.info("请求出错: "+statusCode);
            }else{
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");

                return JSONObject.parseObject(result,new TypeReference<T>(){},Feature.IgnoreNotMatch );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object send(Class<T> clzz) {
        try{
            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                log.info("请求出错: "+statusCode);
            }else{
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return JSONObject.parseObject(result,clzz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
