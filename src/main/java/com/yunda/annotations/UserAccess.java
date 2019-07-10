package com.yunda.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yunda
 * @title: UserAccess 登录权限注解
 * @projectName devicedataserver
 * @description: TODO
 * @date 2019/6/315:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAccess {

    /**
     * 访问允许的角色，默认不限制角色
     */
    String[] value() default "";

    /**
     * 访问是否需要登录，默认需要登录
     */
    boolean needLogin() default true;

}
