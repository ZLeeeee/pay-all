package com.chaotu.pay.config;

import com.chaotu.pay.common.utils.JsonUtils;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.message.MessageHandler;
import com.pdd.pop.sdk.message.WsClient;
import com.pdd.pop.sdk.message.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class PddSDKConfig {
    @Value("${pdd.clientId}")
    private String clientId;
    @Value("${pdd.clientSecret}")
    private String clientSecret;
    @Bean
    public PopClient popClient(){
        PopClient client = new PopHttpClient(clientId, clientSecret);
        return client;
    }
    @Bean
    public PopAccessTokenClient popAccessTokenClient(){
        PopAccessTokenClient client = new PopAccessTokenClient(
                clientId,
                clientSecret);
        return client;
    }
    @Bean
    public WsClient wsClient(){
        WsClient ws = new WsClient(
                clientId,
                clientSecret,
                message -> log.info(JsonUtils.getJosnFromObject(message))
                );
        ws.connect();
        return ws;
    }
}
