package com.chaotu.pay.config;

import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.mq.MsgProducer;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddPmcUserPermitRequest;
import com.pdd.pop.sdk.http.api.response.PddPmcUserPermitResponse;
import com.pdd.pop.sdk.message.MessageHandler;
import com.pdd.pop.sdk.message.WsClient;
import com.pdd.pop.sdk.message.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MsgProducer producer;
    @Value("${pdd.accessToken}")
    private String accessToken;
    @Bean
    public PopClient popClient(){
        PopClient client = new PopHttpClient(clientId, clientSecret);
        return client;
    }
  /*  @Bean
    public PopAccessTokenClient popAccessTokenClient(){
        PopAccessTokenClient client = new PopAccessTokenClient(
                clientId,
                clientSecret);
        return client;
    }*/
    /*@Bean
    public WsClient wsClient(){
        init();
        WsClient ws = new WsClient(
                clientId,
                clientSecret,
                (message) ->{ String msg = JsonUtils.getJosnFromObject(message);
                log.info("pdd回调: "+msg);
                producer.sendAll(msg);}
                );
        ws.connect();
        return ws;
    }
    void init(){
        PddPmcUserPermitRequest request = new PddPmcUserPermitRequest();
        try{
            PddPmcUserPermitResponse response = popClient().syncInvoke(request, accessToken);
            System.out.println(JsonUtil.transferToJson(response));
        }catch ( Exception e){

        }

    }*/
}
