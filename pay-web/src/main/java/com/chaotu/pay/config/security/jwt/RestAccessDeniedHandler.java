package com.chaotu.pay.config.security.jwt;

import com.chaotu.pay.beanUtils.ResponseUtils;
import com.chaotu.pay.common.utils.ResponseUtil;
import com.chaotu.pay.enums.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Date: Created in 21:08 2018/10/27
 * @Author: yaochenglong
 */

@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtils.out(httpServletResponse, ResponseUtil.responseBody(ExceptionCode.HAS_NO_PERMISSION.getCode(),ExceptionCode.HAS_NO_PERMISSION.getMsg()));
    }
}
