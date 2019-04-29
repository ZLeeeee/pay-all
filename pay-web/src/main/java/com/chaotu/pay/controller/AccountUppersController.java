package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.service.AccountUppersService;
import com.chaotu.pay.vo.AccountUppersVo;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

/**
 * 通道商户
 */
@RestController
@RequestMapping("/accountUppers")
@Transactional
public class AccountUppersController {

    @Autowired
    private AccountUppersService accountUppersService;

    /**
     * 分页获取通道商户列表
     * @param pageVo
     * @return
     */
    @PostMapping("/all/page")
    public Message getAllAccountUppers(@RequestBody PageVo pageVo){

        MyPageInfo<AccountUppersVo> pageInfo = null;

        try {
            pageInfo =  accountUppersService.getAllAccountUppers(pageVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }

    /**
     * 添加通道商户
     * @param accountUppersVo
     * @return
     */
    @PostMapping("/add")
    public Message addAccountUppers(@RequestBody AccountUppersVo accountUppersVo){

        if(accountUppersVo==null || StringUtils.isBlank(accountUppersVo.getAccount())){
            return ResponseUtil.responseBody(ExceptionCode.REQUEST_PARAM_ERROR);
        }
        accountUppersService.addAccountUppers(accountUppersVo);
        return ResponseUtil.responseBody("添加通道商户成功");
    }

    /**
     * 修改通道商户
     * @param accountUppersVo
     * @return
     */
    @PostMapping("/edit")
    public Message editAccountUppers(@RequestBody AccountUppersVo accountUppersVo){

        if(accountUppersVo==null ){
            return ResponseUtil.responseBody(ExceptionCode.REQUEST_PARAM_ERROR);
        }
        accountUppersService.editAccountUppers(accountUppersVo);
        return ResponseUtil.responseBody("修改通道商户成功");
    }

    /**
     * 删除通道商户
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
    public Message delAccountUppers(@PathVariable Integer id){
        accountUppersService.delAccountUppers(id);
        return ResponseUtil.responseBody("删除通道商户成功");
    }

}
