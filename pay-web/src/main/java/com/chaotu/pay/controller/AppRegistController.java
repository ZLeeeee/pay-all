package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.qo.WalletQo;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/appPay")
@CacheConfig(cacheNames = "appRegist")
@Transactional
public class AppRegistController {

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
    @PostMapping("/appRegist")
    public Message addAmount(@RequestBody String  str, HttpServletRequest request){

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry entry:
        parameterMap.entrySet()) {
            log.info(entry.getKey()+" : "+entry.getValue());
        }

        log.info(str);
       return ResponseUtil.responseBody(str);
    }
}
