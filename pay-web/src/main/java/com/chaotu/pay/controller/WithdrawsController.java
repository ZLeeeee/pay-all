package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.qo.WithdrawsQo;
import com.chaotu.pay.service.WithdrawsService;
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
 * @description: 结算管理
 * @author: chenyupeng
 * @create: 2019-04-19 10:35
 **/
@Slf4j
@RestController
@RequestMapping("/withdraws")
public class WithdrawsController {
    @Autowired
    private WithdrawsService withdrawsService;

    /**
     * 多条件分页获取订单列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllAgent(@RequestBody WithdrawsQo withdrawsQo){

        Map<String,Object> pageInfo = null;
        PageVo pageVo = withdrawsQo.getPageVo();
        SearchVo searchVo = withdrawsQo.getSearchVo();
        WithdrawsVo withdrawsVo = withdrawsQo.getWithdrawsVo();

        try {
            pageInfo = withdrawsService.findByCondition(pageVo,searchVo,withdrawsVo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }
}
