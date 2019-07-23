package com.chaotu.pay.common.channel;

import com.chaotu.pay.common.choser.Choser;
import com.chaotu.pay.common.choser.ChoserFactory;
import com.chaotu.pay.common.redis.RedisUtils;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.dao.TChannelMapper;
import com.chaotu.pay.po.TChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class RoundChannelChooser implements ChannelChooser {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ChoserFactory choserFactory;
    @Override
    public TChannel getChannel(int payTypeId) {
        Choser<TChannel> chooser = choserFactory.getChannelChooserByPayTypeId(payTypeId);
        TChannel channel = chooser.chose();
        long end = System.currentTimeMillis();
        long start = end - 60*1000;
        if(checkLimitTimes(channel, start , end)){
            return channel;
        }else{
            for (int i = 0; i < chooser.size(); i++) {
                TChannel tChannel = chooser.chose();
                if(checkLimitTimes(channel, start , end))
                    return tChannel;
            }
        }
        return null;
    }
    private boolean checkLimitTimes(TChannel channel,Long min,Long max){
        return redisUtils.zcount(CommonConstant.CHANNEL_ZSET_KEY +channel.getId()
                ,new Double(min),
                new Double(max))
                .compareTo(channel.getLimitTimes()
                        .longValue()) < 0;
    }

}
