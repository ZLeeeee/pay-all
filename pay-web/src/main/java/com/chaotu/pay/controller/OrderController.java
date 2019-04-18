package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.qo.OrderQo;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

/**
 * @description: 订单管理
 * @author: chenyupeng
 * @create: 2019-04-18 10:11
 **/

@Slf4j
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 多条件分页获取用户列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllAgent(@RequestBody OrderQo orderQo){

        Map<String,Object> pageInfo = null;
        PageVo pageVo = orderQo.getPageVo();
        SearchVo searchVo = orderQo.getSearchVo();
        OrderVo orderVo = orderQo.getOrderVo();

        try {
            pageInfo = orderService.findByCondition(pageVo,searchVo,orderVo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }

}
