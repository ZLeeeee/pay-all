package com.chaotu.pay.common.sender;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

@Slf4j
public class PddMerchantSender<T> implements Sender {

    public PddMerchantSender(String url, Object param, String cookie){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Cookie", cookie);
        StringEntity entity = new StringEntity(JSON.toJSONString(param), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        post.setEntity(entity);
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
                return JSON.parseObject(result,new TypeReference<T>(){});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}