package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TYzOrder;
import com.chaotu.pay.qo.PddOrderQo;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.service.YzOrderService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.YzOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/yz/order")
public class YzOrderController {
    @Autowired
    YzOrderService service;


    @PostMapping("/pay")
    public Map<Object,Object> all(@RequestBody YzOrderVo order){
        try{
            Map<Object,Object> resultMap = service.pay(order);
            return resultMap;
        }catch (Exception e){
            Map<Object,Object> resultMap = new HashMap<>();
            resultMap.put("success","0");
            resultMap.put("errCode","-1");
            log.error("下单失败["+ JSON.toJSONString(order) +"]"+e);
            return resultMap;
        }

    }
    @PostMapping("/get")
    public Message get(@RequestBody TYzOrder order){
        return ResponseUtil.responseBody(service.selectOne(order));
    }

    @PostMapping("/notify")
    public Message notify(@RequestBody Map<String,Object> map){
        log.info("1111");
        return ResponseUtil.responseBody("111");
    }

    @GetMapping("/get/{userOrderSn}")
    public Map<String, Object> getByUserOrderSn(@PathVariable String userOrderSn){
        TYzOrder order = new TYzOrder();
        order.setUserOrderNo(userOrderSn);
        TYzOrder order1 = service.selectOne(order);
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo",order1.getId());
        map.put("amount",order1.getAmount());
        map.put("success",(order1.getStatus()>1&&order1.getStatus()<5)?"1":"0");
        map.put("userOrderSn",userOrderSn);
        return result;
    }

    @PostMapping("/send/notify/{id}")
    public Message sendNotify(@PathVariable String id){
        try{
            service.sendNotify(id);
        }catch (Exception e){
            return ResponseUtil.responseBody("-1","回调异常");
        }

        return ResponseUtil.responseBody("111");
    }
}