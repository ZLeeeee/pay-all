package com.chaotu.pay.config;

import com.chaotu.pay.common.channel.Channel;
import com.chaotu.pay.common.redis.RedisUtils;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.TChannel;
import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.service.ChannelService;
import com.chaotu.pay.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    OrderService orderService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ChannelService channelService;
    //发货
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateTodayAmount() {
        TOrder order = new TOrder();
        order.setIsHistory(1);
        orderService.updateByIsHistory(order);

    }
    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleanRedisCache() {
        long timeMillis = System.currentTimeMillis();
        Double start = new Double(timeMillis-60*4*1000);
        Double end = new Double(timeMillis-60*2*1000);
        List<TChannel> channelList = channelService.findAll();
        for (TChannel tChannel : channelList) {
            redisUtils.zremrangeByScore(CommonConstant.CHANNEL_ZSET_KEY+tChannel.getId(),start,end);
        }
    }
}