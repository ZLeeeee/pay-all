package com.chaotu.pay.config.security.jwt;


import com.chaotu.pay.beanUtils.ResponseUtils;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import com.chaotu.pay.handler.LoginFailLimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用户登录失败处理
 * @Date: Created in 20:40 2018/10/27
 * @Author: yaochenglong
 */
@Slf4j
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${tax.loginTimeLimit}")
    private Integer loginTimeLimit;

    @Value("${tax.loginAfterTime}")
    private Integer loginAfterTime;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            recordLoginTime(username);
            String key = "loginTimeLimit:"+username;
            String value = stringRedisTemplate.opsForValue().get(key);
            if(StringUtils.isBlank(value)){
                value = "0";
            }
            //获取已登录错误次数
            int loginFailTime = Integer.parseInt(value);
            int restLoginTime = loginTimeLimit - loginFailTime;
            log.info("用户"+username+"登录失败，还有"+restLoginTime+"次机会");
            if(restLoginTime<=3&&restLoginTime>0){
                ResponseUtils.out(response, ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),"用户名或密码错误，还有"+restLoginTime+"次尝试机会"));

            } else if(restLoginTime<=0) {
                ResponseUtils.out(response,ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),"登录错误次数超过限制，请"+loginAfterTime+"分钟后再试"));
            } else {
                ResponseUtils.out(response,ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),"用户名或密码错误"));
            }
        } else if (e instanceof DisabledException) {

            ResponseUtils.out(response,ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),"账户被禁用，请联系管理员"));
        } else if (e instanceof LoginFailLimitException){

            ResponseUtils.out(response,ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),((LoginFailLimitException) e).getMessage()));
        } else {

            ResponseUtils.out(response,ResponseUtil.responseBody(ExceptionCode.LOGIN_FAILURE.getCode(),e.getMessage()));
        }
    }

    /**
     * 判断用户登陆错误次数
     */
    public boolean recordLoginTime(String username){

        String key = "loginTimeLimit:"+username;
        String flagKey = "loginFailFlag:"+username;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.isBlank(value)){
            value = "0";
        }
        //获取已登录错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
        if(loginFailTime>=loginTimeLimit){
            stringRedisTemplate.opsForValue().set(flagKey, "fail", loginAfterTime, TimeUnit.MINUTES);
            return false;
        }
        return true;
    }
}
