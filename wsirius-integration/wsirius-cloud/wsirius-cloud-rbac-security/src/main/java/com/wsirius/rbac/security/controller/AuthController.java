package com.wsirius.rbac.security.controller;

import com.wsirius.core.base.Result;
import com.wsirius.core.base.WStatus;
import com.wsirius.core.util.Results;
//import com.wsirius.rbac.security.common.ApiResponse;
//import com.wsirius.rbac.security.common.Status;
import com.wsirius.rbac.security.exception.SecurityException;
import com.wsirius.rbac.security.payload.LoginRequest;
import com.wsirius.rbac.security.util.JwtUtil;
import com.wsirius.rbac.security.vo.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 认证 Controller，包括用户注册，用户登录请求
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:23
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String jwt = jwtUtil.createJWT(authentication,loginRequest.getRememberMe());
        return Results.successWithData(new JwtResponse(jwt));
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(WStatus.UNAUTHORIZED);
        }
        //return ApiResponse.ofStatus(Status.LOGOUT);

        return Results.success(WStatus.LOGOUT.toString(),"成功登出");

    }
}
