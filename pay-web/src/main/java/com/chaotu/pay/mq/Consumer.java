package com.chaotu.pay.mq;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.common.utils.ThreadPoolUtil;
import com.chaotu.pay.config.RabbitMQConfig;
import com.chaotu.pay.constant.CommonConstant;
import com.chaotu.pay.po.*;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class Consumer {
    @Autowired
    ChannelService channelService;
    @Autowired
    UserService userService;
    @Autowired
    WalletService walletService;
    @Autowired
    OrderService tOrderService;
    @Autowired
    ChannelAccountService accountService;

    @RabbitListener(id = "a",queues = RabbitMQConfig.QUEUE_A)
    public void processMessage(String content) {
        try {
            log.info("订单: " + content + "已支付");
            TOrder order = JSONObject.parseObject(content, TOrder.class);
            TChannel channel = channelService.findById(order.getChannelId());
            UserVo user = userService.getUserById(order.getUserId());
            TOrder o = new TOrder();
            o.setId(order.getId());
            BigDecimal channelAmount = order.getAmount().multiply(channel.getRate());
            o.setChannelAmount(channelAmount);
            BigDecimal sysAmount = order.getAmount().multiply(user.getRate());
            o.setSysAmount(sysAmount.subtract(channelAmount));
            BigDecimal userAmount = order.getAmount().subtract(sysAmount);
            o.setUserAmount(userAmount);
            o.setStatus(CommonConstant.ORDER_STATUS_PAIED);
            accountService.updateAmount(order.getAmount(), o.getChannelId());
            tOrderService.update(o);
            userService.updateAmount(userAmount,user.getId());
        }catch (Exception e){
            log.error("订单:"+content+"接收异常");
        }
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_B)
    public void processMessage2(String content) {

        log.info("订单: "+content+"回调开始");
       /* Map<String,Object> map = JsonUtils.parseJSON2Map(content);
        String tid = (String) JsonUtils.parseJSON2Map((String) map.get("content")).get("tid");*/

        TOrder order =JSONObject.parseObject(content,TOrder.class);

        if (order == null)
            return;
        TOrder order1 = new TOrder();
        order1.setId(order.getId());
        try {
            SortedMap<Object,Object> params = new TreeMap<>();
            params.put("success","1");
            params.put("orderNo",order.getId());
            params.put("amount",order.getAmount().toString());
            params.put("underOrderNo",order.getUnderOrderNo());
            params.put("userId",order.getUserId());
            UserVo user = userService.getUserById(order.getUserId());
            String key = user.getSignKey();
            String sign = DigestUtil.createSign(params, key);
            params.put("sign",sign);
            Sender<Map<String, Object>> sender = new PddSender<>(order.getNotifyUrl(),params  ,null);
            Map<String, Object> result = sender.send();
            if (result != null) {
                order1.setIsNotify(CommonConstant.ORDER_STATUS_HAS_NOTIFYED);
                tOrderService.update(order1);
            }
        }catch (Exception e){
            order1.setIsNotify(CommonConstant.ORDER_STATUS_HASNT_NOTIFYED);
            tOrderService.update(order1);
            log.info("订单: "+content+"回调异常");
        }
        log.info("订单: "+content+"回调结束");
    }
}
