package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.qo.PddOrderQo;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/pddOrder")
public class PddOrderController {
    @Autowired
    PddOrderService service;


    @PostMapping("/pay")
    public Map<String,Object> all(@RequestBody PddOrderQo order){
        try{

            order.setCreateTime(new Date());
            Map<String,Object> resultMap = service.pay(order);
            return resultMap;
        }catch (Exception e){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("success","0");
            resultMap.put("errCode","-1");
            log.error("下单失败["+ JSON.toJSONString(order) +"]"+e);
            return resultMap;
        }

    }
    @PostMapping("/get")
    public Message get(@RequestBody TPddOrder order){
        return ResponseUtil.responseBody(service.get(order));
    }

    @PostMapping("/notify")
    public Message notify(@RequestBody Map<String,Object> map){
        log.info("1111");
        return ResponseUtil.responseBody("111");
    }

    @GetMapping("/get/{userOrderSn}")
    public Map<String, Object> getByUserOrderSn(@PathVariable String userOrderSn){
        TPddOrder order = new TPddOrder();
        order.setUserOrderSn(userOrderSn);
        TPddOrder order1 = service.get(order);
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("orderSn",order1.getId());
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
