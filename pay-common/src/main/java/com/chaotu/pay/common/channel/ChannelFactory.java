package com.chaotu.pay.common.channel;

import com.chaotu.pay.dao.TChannelAccountMapper;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ChannelFactory {
    private TChannelMapper channelMapper;
    private TChannelAccountMapper channelAccountMapper;
    private Map<Long,Channel> channelMap;
    private Map<Long,TChannelAccount> channelAccountMap;
    @Autowired
    public ChannelFactory(TChannelMapper channelMapper , TChannelAccountMapper channelAccountMapper){
        this.channelMapper = channelMapper;
        this.channelAccountMapper = channelAccountMapper;
        this.channelMap = new ConcurrentHashMap<>();
        this.channelAccountMap = new ConcurrentHashMap<>();
        TChannel channel = new TChannel();
        channel.setId(2L);
        channel = channelMapper.selectOne(channel);
        TChannelAccount account = new TChannelAccount();
        account.setChannelId(channel.getId());
        account = channelAccountMapper.selectOne(account);
        Channel pddChannel = new PddChannel(channel,account);
        TChannel channel2 = new TChannel();
        channel2.setId(3L);
        channel2 = channelMapper.selectOne(channel2);
        TChannelAccount account2 = new TChannelAccount();
        account2.setChannelId(channel2.getId());
        account2 = channelAccountMapper.selectOne(account2);
        Channel pddChannel2 = new PddChannel(channel2,account2);
        channelMap.put(channel.getId(),pddChannel);
        channelAccountMap.put(channel.getId(),account);
        channelMap.put(channel2.getId(),pddChannel2);
        channelAccountMap.put(channel2.getId(),account2);
    }

    public Channel getChannel(Long id){
        return channelMap.get(id);
    }

    public TChannelAccount getChannelAccount(Long channelId){
        return channelAccountMap.get(channelId);
    }

}
