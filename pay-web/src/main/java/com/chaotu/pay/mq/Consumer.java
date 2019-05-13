package com.chaotu.pay.mq;

import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.common.utils.ThreadPoolUtil;
import com.chaotu.pay.config.RabbitMQConfig;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @RabbitListener(queues = RabbitMQConfig.QUEUE_A)
    public void processMessage(String content) {
        TPddOrder o = new TPddOrder();
        o.setOrderSn(content);
        TPddOrder order = orderService.get(o);

        log.info(content+"AAAAAAA");
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_B)
    public void processMessage2(String content) {
        log.info(content+"BBBBBBB");
    }
}
