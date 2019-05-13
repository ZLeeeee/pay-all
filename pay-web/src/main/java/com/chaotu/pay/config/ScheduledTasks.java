package com.chaotu.pay.config;

import com.chaotu.pay.mq.MsgProducer;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.request.PddOrderNumberListGetRequest;
import com.pdd.pop.sdk.http.api.response.PddOrderNumberListGetResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Slf4j
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{
    @Autowired
    public ScheduledTasks (MsgProducer producer, PopClient client, PopAccessTokenClient accessTokenClient,@Value("pdd.code") String code){
        this.producer = producer;
        this.client = client;
        try{
            AccessTokenResponse accessTokenResponse = accessTokenClient.generate(code);
            this.accessToken = accessTokenResponse.getAccessToken();
        }catch (Exception e){
            log.error("获取AccessToken失败",e.getMessage());
        }
    }
    private String accessToken;
    private MsgProducer producer;
    private PopClient client;
    //每1分钟执行一次
    @Scheduled(cron = "0/5 * * * * ? ")
    public void reportCurrentByCron(){
        PddOrderNumberListGetRequest request = new PddOrderNumberListGetRequest();
        request.setOrderStatus(5);
        try{
            PddOrderNumberListGetResponse response = client.syncInvoke(request, accessToken);
            response.getOrderSnListGetResponse().getOrderSnList().forEach((item)->{
                producer.sendAll(item.getOrderSn());
            });
        }catch (Exception e){
            log.error("请求未发货订单失败",e.getMessage());
        }
        producer.sendAll(new Date().toString());
        System.out.println(new Date());
    }


}