package com.chaotu.pay.config;

import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
