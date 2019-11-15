package com.wsirius.security.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.UserService;

/**
 * 登录认证成功处理器
 * 
 * @author bojiangzhou 2018/03/29
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");
        username = StringUtils.defaultIfBlank(username, request.getParameter("mobile"));
        User user = userService.getUserByUsername(username);
        userService.loginSuccess(user.getUserId());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
