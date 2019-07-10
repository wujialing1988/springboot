package com.yunda.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yunda
 * @title: JwtUtil
 * @projectName devicedataserver
 * @description: TODO
 * @date 2019/6/311:08
 */
@Component
@Slf4j
public class JwtUtil {

    // 设置默认秘钥为yunda
    @Value("${jwt.secretKey:yunda}")
    public static String jwtSecret ;

    // 过期时间为 86400 秒 1天
    @Value("${jwt.expiration:86400}")
    public static int jwtExpiration ;

    public int getJwtExpiration() {
        return this.jwtExpiration;
    }

    public static final String CLAIM_KEY_ROLES = "roles";


    public static String generateJwt(String subject, LocalDateTime localDateTime, Map<String, Object> claims) {

        Date issued = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expiration = Date.from(localDateTime.plusSeconds(jwtExpiration).atZone(ZoneId.systemDefault()).toInstant());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuedAt(issued)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret);

        if (claims.get(CLAIM_KEY_ROLES) != null) {
            jwtBuilder.claim(CLAIM_KEY_ROLES, claims.get(CLAIM_KEY_ROLES));
        }

        return jwtBuilder.compact();
    }


    public static String refreshJwt(String jwt) {
        Jws<Claims> claimsJws = validateJwtToken(jwt);
        String subject = claimsJws.getBody().getSubject();
        Object roles = claimsJws.getBody().get(CLAIM_KEY_ROLES);
        Map<String, Object> claims = new HashMap<>();
        if (roles != null) {
            claims.put(CLAIM_KEY_ROLES, roles);
        }

        return generateJwt(subject, LocalDateTime.now(), claims);
    }


    public static Jws<Claims> validateJwtToken(String authToken) {
        Jws<Claims> claimsJws = null;

        if (StringUtils.isNotBlank(authToken)) {
            try {
                claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            } catch (SignatureException e) {
                log.error("Invalid JWT signature -> Message: {} ", e);
            } catch (MalformedJwtException e) {
                log.error("Invalid JWT token -> Message: {}", e);
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token -> Message: {}", e);
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token -> Message: {}", e);
            } catch (IllegalArgumentException e) {
                log.error("JWT claims string is empty -> Message: {}", e);
            }
        }

        return claimsJws;
    }



}
