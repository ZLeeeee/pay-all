package com.chaotu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.service.PaymentService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import com.chaotu.pay.vo.PaymentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 支付方式管理
 */
@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(paymentService.findAllByPage(pageVo));
    }

    /**
     * 添加支付方式
     * @param paymentVo
     * @return
     */
    @PostMapping("/add")
    public Message addPayment(PaymentVo paymentVo,  MultipartFile file) throws Exception{
        paymentService.addPayment(paymentVo,file);
        return ResponseUtil.responseBody("添加支付方式成功");
    }

    /**
     * 修改支付方式
     * @return
     */
    @PostMapping("/edit")
    public Message editPayment(@RequestBody PaymentVo paymentVo){
        paymentService.editPayment(paymentVo);
        return ResponseUtil.responseBody("修改支付方式成功");
    }

    /**
     * 根据id删除支付方式
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
    public Message delPayment(@PathVariable String id){
        paymentService.delPayment(id);
        return ResponseUtil.responseBody("删除支付方式成功");
    }

}
