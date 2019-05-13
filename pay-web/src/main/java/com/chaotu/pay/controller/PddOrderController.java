package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddGoods;
import com.chaotu.pay.po.TPddOrder;
import com.chaotu.pay.service.PddGoodsService;
import com.chaotu.pay.service.PddOrderService;
import com.chaotu.pay.vo.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pddOrder")
public class PddOrderController {
    @Autowired
    PddOrderService service;

    @PostMapping("/pay")
    public Message all(@RequestBody TPddOrder order){
        String returnUrl = service.pay(order);
        if(StringUtils.isNotBlank(returnUrl))
            return ResponseUtil.responseBody(returnUrl);
        return ResponseUtil.responseBody("1","请求失败");
    }
    @PostMapping("/get")
    public Message get(@RequestBody TPddOrder order){
        return ResponseUtil.responseBody(service.get(order));
    }

}
