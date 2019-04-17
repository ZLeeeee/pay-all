package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.service.OrderService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:订单
 * @author: chenyupeng
 * @create: 2019-04-17 14:09
 **/
@Slf4j
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 分页查询所有角色
     * @return
     */
    @GetMapping("/getAllOrder/page")
    public Message getAllOrderForPage(PageVo pageVo){
        return ResponseUtil.responseBody(orderService.getAllOrderForPage(pageVo, null));
    }
}
