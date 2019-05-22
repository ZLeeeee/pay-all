package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.service.BankCradService;
import com.chaotu.pay.vo.BankCardVo;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 银行卡信息管理
 */
@RestController
@RequestMapping("/bankcard")
@Transactional
public class BankCardController {

    @Autowired
    private BankCradService bankCradService;

    /**
     * 获取所有银行
     * @return
     */
    @GetMapping("/allBank")
    public Message getAllBank(){
        return ResponseUtil.responseBody(bankCradService.getAllBank());
    }

    /**
     * 分页查询银行卡信息
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(bankCradService.findAllByPage(pageVo));
    }

    @PostMapping("/all/byUser")
    public Message findAllByUser(){
        return ResponseUtil.responseBody(bankCradService.findAllByUser());
    }

    /**
     * 添加银行卡
     * @param bankCardVo
     * @return
     */
    @PostMapping("/add")
    public Message addBankCard(@RequestBody BankCardVo bankCardVo){
        bankCradService.addBankCard(bankCardVo);
        return ResponseUtil.responseBody("添加银行卡成功");
    }

    /**
     * 修改银行卡
     * @param bankCardVo
     * @return
     */
    @PostMapping("/edit")
    public Message editBankCard(@RequestBody BankCardVo bankCardVo){
        bankCradService.editBankCard(bankCardVo);
        return ResponseUtil.responseBody("更新成功");
    }

    /**
     * 删除银行卡
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
    public Message delBankCard(@PathVariable String id){
        bankCradService.delBankCard(id);
        return ResponseUtil.responseBody("删除成功");
    }

}
