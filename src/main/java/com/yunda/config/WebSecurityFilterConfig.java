package com.yunda.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author yunda
 * @title: WebSecurityFilterConfig 注册权限过滤器
 * @projectName devicedataserver
 * @description: TODO
 * @date 2019/6/314:54
 */
public class WebSecurityFilterConfig {

    /**
     * 权限过滤器
     */
    @Resource
    private WebSecurityFilter webSecurityFilter ;

    @Bean
    public FilterRegistrationBean webSecurityFilterRegistration(){
        FilterRegistrationBean<WebSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(webSecurityFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }

}
