package com.yunda.frame.user.controller;


import com.google.common.collect.ImmutableMap;
import com.yunda.business.test.entity.Test;
import com.yunda.domain.Response;
import com.yunda.frame.user.entity.User;
import com.yunda.frame.user.service.IUserService;
import com.yunda.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wujl
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/user/user")
public class UserController {

    /**
     * 用户服务
     */
    @Resource
    private IUserService userService ;

    /**
     * web端登录
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/webLogin")
    public Response webLogin(@RequestBody @Valid User entity, HttpServletRequest request, HttpServletResponse response){
        Response result = userService.webLogin(entity);
        // 成功后，设置cookie信息
        if(result.getCode().equals(Response.successCode)){
            String jwt = "Bearer " + JwtUtil.generateJwt("get user id from some where", LocalDateTime.now(),
                    ImmutableMap.of(JwtUtil.CLAIM_KEY_ROLES, "some roles"));
            Cookie jwtCookie = null;
            try {
                jwtCookie = new Cookie("Authorization", URLEncoder.encode(jwt, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(JwtUtil.jwtExpiration);
            response.addCookie(jwtCookie);
            response.addHeader("Authorization", jwt);
        }
        return result;
    }

}
