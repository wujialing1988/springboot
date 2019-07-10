package com.yunda.config;


import com.yunda.annotations.UserAccess;
import com.yunda.frame.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yunda
 * @title: WebSecurityAspect
 * @projectName devicedataserver
 * @description: TODO
 * @date 2019/6/315:06
 */
@Aspect
@Component
@Slf4j
public class WebSecurityAspect {

    @Before(value = "@annotation(com.yunda.annotations.UserAccess)")
    public void authoritiesCheck(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("user access security check for method: {}", methodName);

        try {
            UserAccess userAccess = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(UserAccess.class);
            //如果是不需要登录就可以访问的API，则直接放行
            if (!userAccess.needLogin()) {
                return;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            User user = (User) request.getAttribute(WebSecurityFilter.SECURITY_USER);
            //需要登录才能访问的API，没有发现用户，返回禁止
            if (user == null) {
                throw new Exception("User Not Found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
