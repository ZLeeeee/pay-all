package com.chaotu.pay.mq;

import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.common.utils.ThreadPoolUtil;
import com.chaotu.pay.config.RabbitMQConfig;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.service.*;
import com.chaotu.pay.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class Consumer {
    @Autowired
    PddGoodsService goodsService;
    @Autowired
    PddOrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    WalletService walletService;
    @Autowired
    UserRatesService userRatesService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_A)
    public void processMessage(String content) {
        log.info("订单: "+content+"已支付");
        Map<String,Object> map = JsonUtils.parseJSON2Map(content);
        String tid = (String) JsonUtils.parseJSON2Map((String) map.get("content")).get("tid");
        TPddOrder order = new TPddOrder();
        order.setOrderSn(tid);
        order.setStatus((byte)2);
        TWallet wallet = new TWallet();
        wallet.setUserId(order.getUserId());
        wallet.setType("2");
        TUserRates userRates = new TUserRates();
        userRates.setUserId(order.getUserId());
        userRates.setChannelId(1);
        TUserRates tUserRates = userRatesService.get(userRates);
        walletService.editAmount(wallet, order.getAmount().subtract(order.getAmount().multiply(tUserRates.getRate())).toString(),"0");
        orderService.updateByOrderSn(order);
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_B)
    public void processMessage2(String content) {
        log.info("订单: "+content+"回调开始");
        Map<String,Object> map = JsonUtils.parseJSON2Map(content);
        String tid = (String) JsonUtils.parseJSON2Map((String) map.get("content")).get("tid");
        TPddOrder order = orderService.getByOrderSn(tid);
        if (order == null)
            return;
        try {
            Map<String,String> params = new HashMap<>();
            params.put("status","1");
            Sender<Map<String, Object>> sender = new PddSender<>(order.getNotifyUrl(),params  ,null);
            Map<String, Object> result = sender.send();
            if (result != null) {
                order.setNotifyTimes(6);
                orderService.edit(order);
            }
        }catch (Exception e){
            order.setNotifyTimes(1);
            orderService.edit(order);
            log.info("订单: "+content+"回调异常");
        }
        log.info("订单: "+content+"回调结束");
    }
}
