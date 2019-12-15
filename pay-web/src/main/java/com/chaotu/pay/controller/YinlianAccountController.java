package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TYinlianAccount;
import com.chaotu.pay.service.ChannelAccountService;
import com.chaotu.pay.service.YinlianAccountService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡信息管理
 */
@RestController
@RequestMapping("/yinlian/account")
public class YinlianAccountController {

    @Autowired
    private YinlianAccountService accountService;

    /**
     * 获取所有
     * @return
     */
    @GetMapping("/all")
    public Message getAll(){
        return ResponseUtil.responseBody(accountService.findAll());
    }

    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message findAllByPage(@RequestBody PageVo pageVo){
        return ResponseUtil.responseBody(accountService.findAllByPage(pageVo));
    }


    /**
     * 添加
     * @param account
     * @return
     */
    @PostMapping("/add")
    public Message addBankCard(@RequestBody TYinlianAccount account){
        account.setTodayAmount(new BigDecimal(0));
        account.setTotalAmount(new BigDecimal(0));
        account.setCreateTime(new Date());
        accountService.insert(account);

        return ResponseUtil.responseBody("添加成功");
    }

    /**
     * 修改
     * @param account
     * @return
     */
    @PostMapping("/update")
    public Message update(@RequestBody TYinlianAccount account){
        accountService.update(account);
        return ResponseUtil.responseBody("更新成功");
    }

    /**
     * 删除
     * @param account
     * @return
     */
    @PostMapping("/del")
    public Message delete(@RequestBody TYinlianAccount account){
        accountService.delete(account);
        return ResponseUtil.responseBody("删除成功");
    }

}
