package com.yunda.config;

import com.yunda.frame.user.entity.User;
import com.yunda.frame.user.service.IUserService;
import com.yunda.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @author yunda
 * @title: WebSecurityFilter 解析请求
 * @projectName devicedataserver
 * @description: TODO
 * @date 2019/6/310:57
 */
@Component
@Slf4j
public class WebSecurityFilter extends OncePerRequestFilter {


    public static final String SECURITY_USER = "SECURITY_USER";

    /**
     * 用户业务
     */
    @Resource
    private IUserService userService ;

    @Resource
    private JwtUtil jwtUtil ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = getJwt(request);

            if (StringUtils.isNotBlank(jwt) && jwtUtil.validateJwtToken(jwt) != null) {
                Jws<Claims> claimsJws = jwtUtil.validateJwtToken(jwt);
                String userUid = claimsJws.getBody().getSubject();

                //获取相应的用户信息，可以在过滤器中先行获取，也可以先保存用户ID，在需要时进行获取
                Optional<User> optionalUser = userService.findUserByUid(userUid);

                if (optionalUser.isPresent()) {
                    request.setAttribute(SECURITY_USER, optionalUser.get());

                    jwt = "Bearer " + jwtUtil.refreshJwt(jwt);

                    Cookie jwtCookie = new Cookie("Authorization", URLEncoder.encode(jwt, "UTF-8"));
                    jwtCookie.setHttpOnly(true);
                    jwtCookie.setMaxAge(jwtUtil.getJwtExpiration());
                    jwtCookie.setPath("/");
                    response.addCookie(jwtCookie);

                    response.addHeader("Authorization", jwt);
                }

            }

        } catch (Exception e) {
            log.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }


    private String getJwt(HttpServletRequest request) {

        //先从header中获取
        String authHeader = request.getHeader("Authorization");

        //再从cookie中获取
        if (StringUtils.isBlank(authHeader)) {
            try {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("Authorization".equals(cookie.getName())) {
                            authHeader = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Can NOT get jwt from cookie -> Message: {}", e);
            }
        }

        if (StringUtils.startsWith(authHeader, "Bearer ")) {
            authHeader = authHeader.replace("Bearer ", "");
        }

        return authHeader;
    }
}
