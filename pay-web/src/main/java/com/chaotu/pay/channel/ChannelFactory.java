package com.chaotu.pay.channel;

import com.chaotu.pay.service.ChannelAccountService;
import com.chaotu.pay.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ChannelFactory {
    private ChannelService channelService;
    private ChannelAccountService channelAccountService;
    @Autowired
    public ChannelFactory(ChannelService channelService , ChannelAccountService channelAccountService){
        this.channelAccountService = channelAccountService;
        this.channelService = channelService;
    }

}
