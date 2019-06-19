package com.chaotu.pay.mq;

import com.alibaba.fastjson.JSONObject;
import com.chaotu.pay.common.sender.PddSender;
import com.chaotu.pay.common.sender.Sender;
import com.chaotu.pay.common.utils.DigestUtil;
import com.chaotu.pay.common.utils.JsonUtils;
import com.chaotu.pay.common.utils.ThreadPoolUtil;
import com.chaotu.pay.config.RabbitMQConfig;
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
    YzGoodsService goodsService;
    @Autowired
    YzOrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    WalletService walletService;
    @Autowired
    OrderService tOrderService;
    @Autowired
    YzAccountService accountService;

    @RabbitListener(id = "a",queues = RabbitMQConfig.QUEUE_A)
    public void processMessage(String content) {
        try {
            log.info("订单: " + content + "已支付");
       /* Map<String,Object> map = JsonUtils.parseJSON2Map(content);
        String tid = (String) JsonUtils.parseJSON2Map((String) map.get("content")).get("tid");*/
            // TYzOrder order = orderService.getByOrderSn(content);

            //TYzOrder order1 = JsonUtils.getObjectFromJson(content,TYzOrder.class);
            TYzOrder order = JSONObject.parseObject(content, TYzOrder.class);
            /* order.setStatus((byte)2);*/
            TYzOrder order1 = new TYzOrder();
            order1.setId(order.getId());
            order1.setStatus((byte) 3);
            TWallet wallet = new TWallet();
            wallet.setUserId(order.getUserId());
            wallet.setType("2");
            UserVo user = userService.getUserById(order.getUserId());
            TOrder o = new TOrder();
            o.setCreateTime(new Date());
            o.setOnorderno(order.getId());
            BigDecimal sysAmount = order.getAmount().multiply(user.getRate());
            o.setSysamount(sysAmount);
            BigDecimal userAmount = order.getAmount().subtract(sysAmount);
            o.setUseramount(userAmount);
            o.setStatus((byte) 1);

            accountService.updateAmount(order.getAmount(), order.getYzAccountId());

            tOrderService.updateaByOrderNo(o);
            walletService.editAmount(wallet, userAmount.toString(), "0");
            orderService.update(order1);
        }catch (Exception e){
            log.error("订单:"+content+"接收异常");
        }
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_B)
    public void processMessage2(String content) {

        log.info("订单: "+content+"回调开始");
       /* Map<String,Object> map = JsonUtils.parseJSON2Map(content);
        String tid = (String) JsonUtils.parseJSON2Map((String) map.get("content")).get("tid");*/

        TYzOrder order =JSONObject.parseObject(content,TYzOrder.class);

        if (order == null)
            return;
        TYzOrder order1 = new TYzOrder();
        order1.setId(order.getId());
        try {
            SortedMap<Object,Object> params = new TreeMap<>();
            params.put("success","1");
            params.put("orderNo",order.getId());
            params.put("amount",order.getAmount().toString());
            params.put("userOrderNo",order.getUserOrderNo());
            params.put("userId",order.getUserId());
            UserVo user = userService.getUserById(order.getUserId());
            String key = user.getSignKey();
            String sign = DigestUtil.createSign(params, key);
            params.put("sign",sign);
            Sender<Map<String, Object>> sender = new PddSender<>(order.getNotifyUrl(),params  ,null);
            Map<String, Object> result = sender.send();
            if (result != null) {
                order1.setNotifyTimes(6);
                orderService.update(order1);
            }
        }catch (Exception e){
            order1.setNotifyTimes(1);
            orderService.update(order1);
            log.info("订单: "+content+"回调异常");
        }
        log.info("订单: "+content+"回调结束");
    }
}
