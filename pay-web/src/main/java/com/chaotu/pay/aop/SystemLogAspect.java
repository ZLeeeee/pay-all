package com.chaotu.pay.aop;

import cn.hutool.core.util.StrUtil;
import com.chaotu.pay.annotation.SystemLog;
import com.chaotu.pay.beanUtils.IpInfoUtil;
import com.chaotu.pay.common.utils.ThreadPoolUtil;
import com.chaotu.pay.service.LogService;
import com.chaotu.pay.vo.LogVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @Description: Spring AOP实现日志管理
 * @Date: Created in 9:09 2018/11/7
 * @Author: yaochenglong
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Autowired
    private LogService logService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * Controller层切点,注解方式
     */
    //@Pointcut("execution(* *..controller..*Controller*.*(..))")
    @Pointcut("@annotation(com.chaotu.pay.annotation.SystemLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {

        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
    }


    /**
     * 后置通知(在方法执行之后返回) 用于拦截Controller层操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();

            if (StrUtil.isNotBlank(username)) {


                LogVo logVo = new LogVo();

                //日志标题
                logVo.setName(getControllerMethodDescription(joinPoint));
                //日志请求url
                logVo.setRequestUrl(request.getRequestURI());
                //请求方式
                logVo.setRequestType(request.getMethod());
                //请求参数
                Map<String, String[]> logParams = request.getParameterMap();
                logVo.setMapToParams(logParams);
                //请求用户
                logVo.setUsername(username);
                //请求IP
                logVo.setIp(ipInfoUtil.getIpAddr(request));
                //IP地址
                //logVo.setIpInfo(ipInfoUtil.getIpCity(ipInfoUtil.getIpAddr(request)));
                //请求开始时间
                Date logStartTime = beginTimeThreadLocal.get();

                //调用线程保存至ES
                ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(logVo, logService));

            }
        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
    }

    /**
     * 保存日志至数据库
     */
    private static class SaveSystemLogThread implements Runnable {

        private LogVo logVo;
        private LogService logService;

        public SaveSystemLogThread(LogVo logVo, LogService logService) {
            this.logVo = logVo;
            this.logService = logService;
        }

        @Override
        public void run() {

            logService.add(logVo);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {

        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if (clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，原因是方法可以重载哦
                continue;
            }
            description = method.getAnnotation(SystemLog.class).description();
        }
        return description;
    }

}