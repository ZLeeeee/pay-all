package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/pddOrder")
public class PddOrderController {
    @Autowired
    PddOrderService service;

    @PostMapping("/pay")
    public Message all(@RequestBody TPddOrder order){
        order.setCreateTime(new Date());
        String returnUrl = service.pay(order);
        if(StringUtils.isNotBlank(returnUrl))
            return ResponseUtil.responseBody(returnUrl);
        return ResponseUtil.responseBody("1","请求失败");
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


}
