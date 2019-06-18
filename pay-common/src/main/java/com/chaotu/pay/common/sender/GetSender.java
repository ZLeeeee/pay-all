package com.chaotu.pay.common.sender;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@Slf4j
public class GetSender<T> implements Sender<T> {

    public GetSender(String url, String cookie){
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.setHeader("Cookie", cookie);
        get.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; SM-G892A Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/67.0.3396.87 Mobile Safari/537.36");
        // 发送Json格式的数据请求
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).build();
        get.setConfig(requestConfig);
        this.get = get;
        this.client = client;
    }
    private HttpGet get;
    private HttpClient client;
    @Override
    public T send() {
        try{
            HttpResponse response = client.execute(get);
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
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                log.info("请求出错: "+statusCode);
            }else{
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                if(String.class.equals(clzz))
                    return result;
                return JSONObject.parseObject(result,clzz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
