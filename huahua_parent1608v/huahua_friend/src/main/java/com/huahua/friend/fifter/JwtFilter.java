/**
 * Date:    2019/4/26 16:40
 * <author>
 * 陈柏
 */
package com.huahua.friend.fifter;

import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        System.out.println("经过拦截器");
        final String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            final String token = authHeader.substring(7);
            Claims claims = null;
            claims = jwtUtil.parseJWT(token);
            if(null != claims){
                if("admin".equals(claims.get("roles"))){
                    //如果是管理员存入管理员身份
                    request.setAttribute("admin_claims",claims);
                }if("user".equals(claims.get("roles"))){
                    //如果是用户存用户身份
                    request.setAttribute("user_claims",claims);
                }
            }
        }
        return true;
    }

}
