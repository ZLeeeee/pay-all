package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TPddAccount;
import com.chaotu.pay.service.PddAccountService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡信息管理
 */
@RestController
@RequestMapping("/pddAccount")
@Transactional
public class PddAccountController {

    @Autowired
    private PddAccountService accountService;

    /**
     * 获取所有银行
     * @return
     */
    @GetMapping("/all")
    public Message getAllBank(){
        return ResponseUtil.responseBody(accountService.findAllByStatus());
    }

    /**
     * 分页查询银行卡信息
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(accountService.findAllByPage(pageVo));
    }


    /**
     * 添加银行卡
     * @param account
     * @return
     */
    @PostMapping("/add")
    public Message addBankCard(@RequestBody TPddAccount account){
        account.setTodayAmount(new BigDecimal(0));
        account.setTotalAmount(new BigDecimal(0));
        account.setCreateTime(new Date());
        accountService.insert(account);

        return ResponseUtil.responseBody("添加成功");
    }

    /**
     * 修改银行卡
     * @param account
     * @return
     */
    @PostMapping("/edit")
    public Message editBankCard(@RequestBody TPddAccount account){
        if("".equals(account.getCookie())) {
           ResponseUtil.responseBody("1","cookie不可为空");
        }

        accountService.update(account);
        return ResponseUtil.responseBody("更新成功");
    }

    /**
     * 删除银行卡
     * @param account
     * @return
     */
    @PostMapping("/del")
    public Message delBankCard(@RequestBody TPddAccount account){

        accountService.delete(account);
        return ResponseUtil.responseBody("删除成功");
    }

}
