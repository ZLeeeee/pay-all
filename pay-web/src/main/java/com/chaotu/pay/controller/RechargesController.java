package com.chaotu.pay.controller;


import com.chaotu.pay.beanUtils.ResponseUtils;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.qo.RechargesQo;
import com.chaotu.pay.service.RechargesService;
import com.chaotu.pay.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 用户充值
 */
@RestController
@RequestMapping("/recharges")
public class RechargesController {

    @Autowired
    private RechargesService rechargesService;


    @GetMapping("/all")
    public Message getAllRecharges(){
        return ResponseUtil.responseBody(rechargesService.getAll());
    }

    /**
     * 分页查询充值记录
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(rechargesService.findAllByPage(pageVo,null));
    }

    /**
     * 多条件分页获取充值记录列表
     * @param rechargesQo
     * @return
     */
    @PostMapping("/search")
    public Message search(@RequestBody RechargesQo rechargesQo){
        PageVo pageVo = rechargesQo.getPageVo();
        RechargeVo rechargeVo = rechargesQo.getRechargeVo();
        SearchVo searchVo = rechargesQo.getSearchVo();
        MyPageInfo<RechargeVo> pageInfo = null;
        try {
            pageInfo = rechargesService.search(pageVo,searchVo,rechargeVo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }


}
