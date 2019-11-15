package com.wsirius.security.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.wsirius.core.message.MessageAccessor;
import com.wsirius.security.config.SecurityProperties;
import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.UserService;
import com.wsirius.security.exception.PasswordErrorException;

/**
 * 登录失败处理器
 * 
 * @author bojiangzhou 2018/03/29
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private UserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.setAttribute("username", username);
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                            MessageAccessor.getMessage(exception.getMessage(), exception.getMessage()));
        }
        if (exception instanceof PasswordErrorException) {
            User user = userService.getUserByUsername(username);
            userService.loginFail(user.getUserId());
        }

        redirectStrategy.sendRedirect(request, response, securityProperties.getLoginPage() + "?username=" + username);
    }
}
