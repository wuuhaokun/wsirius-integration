package com.wsirius.security.social.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wsirius.core.message.MessageAccessor;
import com.wsirius.security.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Social 登录失败处理器
 * 
 * @author bojiangzhou 2018/03/29
 */
public class CustomSocialAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                            MessageAccessor.getMessage(exception.getMessage(), exception.getMessage()));
        }

        redirectStrategy.sendRedirect(request, response, securityProperties.getLoginPage());
    }
}
