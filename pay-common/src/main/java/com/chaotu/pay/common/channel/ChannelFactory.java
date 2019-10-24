package com.chaotu.pay.common.channel;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.dao.TChannelAccountMapper;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TChannelAccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Slf4j
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
        registChannel(2,PddChannel.class);
        registChannel(3,Ali2BankHtmlChannel.class);
        registChannel(4,MachiPayChannel.class);
        registChannel(5,QianBaoChannel.class);
        registChannel(6,RongHeChannel.class);
        registChannel(7,RongHeChannelAli.class);
        registChannel(8,USDTChannel.class);
        registChannel(9,SaoMaFuChannel.class);
        registChannel(10,YzChannel.class);
        registChannel(11,SuDaHtmlChannel.class);
        registChannel(12,Ali2BankHtmlChannel.class);
        registChannel(13,RongYiFuChannel.class);
        registChannel(14,HiPayChannel.class);
        registChannel(15,YouKuaiChannel.class);
        registChannel(16,YouKuaiChannel.class);
        registChannel(17,XiAnTaoBaChannel.class);
        registChannel(18,MyPayChannel.class);
        registChannel(19,NewRongYiFuChannel.class);
        registChannel(20,TTChannel.class);
        registChannel(21,TTChannel.class);
        registChannel(22,RenRenChannel.class);
        registChannel(23,RongYiFuChannelNew.class);
        registChannel(24,JingYiTongChannel.class);
        registChannel(25,JuXinChannel.class);
        registChannel(26,TTChannel.class);
        registChannel(27, WuHanWuChannel.class);
        registChannel(28, ChaoRenChannel.class);
        registChannel(29, MachiPayChannel.class);
        registChannel(30, TTChannel.class);
    }

    public Channel getChannel(Long id){
        return channelMap.get(id);
    }

    public TChannelAccount getChannelAccount(Long channelId){
        return channelAccountMap.get(channelId);
    }

    private void registChannel(long id,Class<? extends Channel> clzz){
        TChannel channel = new TChannel();
        channel.setId(id);
        channel = channelMapper.selectOne(channel);
        if(channel == null)
            return;
        TChannelAccount account = new TChannelAccount();
        account.setChannelId(channel.getId());
        account = channelAccountMapper.selectOne(account);

        try{
            Channel channel1 = (Channel) (clzz.getConstructor(TChannel.class, TChannelAccount.class).newInstance(channel, account));
            channelMap.put(channel.getId(),channel1);
            channelAccountMap.put(channel.getId(),account);
            log.info("通道:"+ JSONObject.toJSONString(channel)+"已注册");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
