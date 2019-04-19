package com.chaotu.pay.controller;

import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.po.TUser;
import com.chaotu.pay.po.TUserRates;
import com.chaotu.pay.qo.UserRatesQo;
import com.chaotu.pay.qo.WalletQo;
import com.chaotu.pay.service.UserRatesService;
import com.chaotu.pay.service.UserService;
import com.chaotu.pay.service.WalletService;
import com.chaotu.pay.vo.Message;
import com.chaotu.pay.vo.UserVo;
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
import java.util.List;

/**
 * @Description:
 * @Author: yaochenglong
 * @Date: 16:21 2018/10/21
 */
@Slf4j
@RestController
@RequestMapping("/userRates")
@CacheConfig(cacheNames = "userRates")
@Transactional
public class UserRatesController {

    @Autowired
    private UserRatesService userRatesService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 根据用户获取UserRates
     *
     * @return
     */
    @PostMapping("/get/byUser")
    public Message getUserRatesByUser(@RequestBody UserVo vo) {
        if (StringUtils.isBlank(vo.getId())) {
            return ResponseUtil.responseBody("-1", "参数有误");
        } else {
            return ResponseUtil.responseBody(userRatesService.getUserRatesByUser(vo));
        }
    }

    /**
     * 根据ID获取UserRates
     *
     * @return
     */
    @PostMapping("/get/byId")
    public Message getUserRates(@RequestBody TUserRates userRates) {
        if (userRates.getId() == 0) {
            return ResponseUtil.responseBody("-1", "参数有误");
        } else {
            return ResponseUtil.responseBody(userRatesService.getUserRatesById(userRates));
        }
    }

    /**
     * 根据ID获取UserRates
     *
     * @return
     */
    @PostMapping("/edit")
    public Message editUserRates(@RequestBody TUserRates userRates) {
        if (userRates.getId() == 0) {
            return ResponseUtil.responseBody("-1", "参数有误");
        } else {
            userRatesService.editUserRates(userRates);
            return ResponseUtil.responseBody("1","修改成功");
        }
    }
    /**
     * 根据ID获取UserRates
     * @return
     */
    @PostMapping("/edit/byUserId")
    public Message editUserRatesByUserId(@RequestBody UserRatesQo qo){
        if(StringUtils.isBlank(qo.getUserId())||qo.getUserRatesVo()==null) {
            return ResponseUtil.responseBody("-1", "参数有误");
        } else{
            List<TUser> users = userService.findByParentId(qo.getUserId());
            userRatesService.editUserRatesByUserId(qo.getUserRatesVo(),qo.getUserId());
            return ResponseUtil.responseBody("1","修改成功");
        }
    }
}
