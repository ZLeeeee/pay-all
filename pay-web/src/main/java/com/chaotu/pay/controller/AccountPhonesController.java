package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.qo.AccountPhonesQo;
import com.chaotu.pay.qo.UserQo;
import com.chaotu.pay.service.AccountPhonesService;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/accountPhones")
@CacheConfig(cacheNames = "accountPhones")
@Transactional
public class AccountPhonesController {

    @Autowired
    private AccountPhonesService accountPhonesService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 多条件分页获取用户列表
     * @return
     */
    @PostMapping("/all")
    public Message getAllUser(@RequestBody AccountPhonesQo qo){
        PageVo pageVo = qo.getPageVo();
        SearchVo searchVo = qo.getSearchVo();
        AccountPhonesVo vo = qo.getAccountPhonesVo();
        MyPageInfo<AccountPhonesVo> pageInfo = null;
        try {
            pageInfo = accountPhonesService.findByCondition(pageVo,searchVo,vo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseUtil.responseBody(pageInfo);
    }


    @PostMapping("/add")
    public Message add(@RequestBody AccountPhonesVo vo){
        if(vo==null){
            throw new BizException(ExceptionCode.REQUEST_PARAM_ERROR);
        }
        accountPhonesService.addAccountPhonesVo(vo);
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
            accountPhonesService.delAccountPhonesVo(id);
        }
        return ResponseUtil.responseBody("删除用户成功");
    }

    /***
     * 编辑账户
     * @param vo
     * @return
     */
    @PostMapping("/edit")
    public Message editUser(@RequestBody AccountPhonesVo vo){
        accountPhonesService.editAccountPhonesVo(vo);
        return ResponseUtil.responseBody("修改用户成功");
    }


}
