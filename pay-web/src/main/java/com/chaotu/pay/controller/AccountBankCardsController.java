package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.qo.AccountBankCardsQo;
import com.chaotu.pay.qo.AccountPhonesQo;
import com.chaotu.pay.service.AccountBankCardsService;
import com.chaotu.pay.service.AccountPhonesService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/accountBankCards")
@CacheConfig(cacheNames = "accountBankCards")
@Transactional
public class AccountBankCardsController {

    @Autowired
    private AccountBankCardsService accountBankCardsService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 多条件分页获取用户列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllUser(@RequestBody AccountBankCardsQo qo){
        PageVo pageVo = qo.getPageVo();
        SearchVo searchVo = qo.getSearchVo();
        AccountBankCardsVo vo = qo.getAccountBankCardsVo();
        MyPageInfo<AccountBankCardsVo> pageInfo = null;
        try {
            pageInfo = accountBankCardsService.findByCondition(pageVo,searchVo,vo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }


    @PostMapping("/add")
    public Message add(@RequestBody AccountBankCardsVo vo){
        if(StringUtils.isBlank(vo.getCardno())||StringUtils.isBlank(vo.getAccounttype())||StringUtils.isBlank(vo.getPhoneId())||StringUtils.isBlank(vo.getBankAccount())){
            throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR);
        }
        accountBankCardsService.addAccountBankCardsVo(vo);
        return ResponseUtil.responseBody("添加成功");
    }


    /**
     * 通过id逻辑删除账户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    public Message delAllByIds(@PathVariable String[] ids){
        for(String id:ids){
            accountBankCardsService.delAccountPhonesVo(id);
        }
        return ResponseUtil.responseBody("删除用户成功");
    }

    /***
     * 编辑账户
     * @param vo
     * @return
     */
    @PostMapping("/edit")
    public Message editUser(@RequestBody AccountBankCardsVo vo){
        accountBankCardsService.editAccountBankCards(vo);
        return ResponseUtil.responseBody("修改用户成功");
    }


}
