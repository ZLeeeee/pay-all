package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.po.TWallet;
import com.chaotu.pay.qo.UserQo;
import com.chaotu.pay.qo.WalletQo;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.BizException;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.MyPageInfo;
import com.chaotu.pay.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
@CacheConfig(cacheNames = "wallet")
@Transactional
public class WalletController {

    @Autowired
    private WalletService walletService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 编辑余额
     * @return
     */
    @PostMapping("/amount/edit")
    public Message addAmount(@RequestBody WalletQo wallet){
        if(StringUtils.isBlank(wallet.getAmount())||Double.parseDouble(wallet.getAmount())<=0|| StringUtils.isBlank(wallet.getWalletVo().getUserId()))
            return ResponseUtil.responseBody("-1","参数有误");
        int i = walletService.editAmount(wallet.getWalletVo(),wallet.getAmount(),wallet.getOption());
        if(i > 0)
            return ResponseUtil.responseBody("修改余额成功");
        else
            return ResponseUtil.responseBody("-1","余额不足");
    }
}
