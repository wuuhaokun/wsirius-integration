package com.wsirius.security.social.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsirius.core.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationToken;

/**
 * Social登录认证成功处理器
 * 
 * @author bojiangzhou 2018/03/29
 */
public class CustomSocialAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
        // 将 CustomSocialUserDetails 转换成 CustomUserDetails
        if (authentication instanceof SocialAuthenticationToken
                && authentication.getPrincipal() instanceof CustomSocialUserDetails) {
            CustomSocialUserDetails socialUserDetails = (CustomSocialUserDetails) authentication.getPrincipal();
            CustomUserDetails userDetails = new CustomUserDetails(
                    socialUserDetails.getUsername(),
                    "",
                    Long.valueOf(socialUserDetails.getUserId()),
                    socialUserDetails.getNickname(),
                    socialUserDetails.getLanguage(),
                    socialUserDetails.getAuthorities()
            );
            SocialAuthenticationToken socialAuthenticationToken = (SocialAuthenticationToken) authentication;
            socialAuthenticationToken.setDetails(userDetails);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
