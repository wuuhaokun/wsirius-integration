package com.wsirius.security.core;

import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.ConfigService;
import com.wsirius.security.domain.service.UserService;
import com.wsirius.security.exception.AccountNotExistsException;
import com.wsirius.security.exception.CaptchaException;
import com.wsirius.security.exception.PasswordErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证器
 *
 * @author bojiangzhou 2018/09/09
 */
@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService detailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfigService configService;


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new AccountNotExistsException("user.error.login.username-or-password.error");
        }
        if (!user.isEnabled()) {
            throw new DisabledException("user.error.login.user.disabled");
        }
        return detailsService.loadUserByUsername(username);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);

        // 检查验证码
        if (authentication.getDetails() instanceof CustomWebAuthenticationDetails) {
            if (configService.isEnableCaptcha(user.getPasswordErrorTime())) {
                CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
                String inputCaptcha = details.getInputCaptcha();
                String cacheCaptcha = details.getCacheCaptcha();
                if (StringUtils.isEmpty(inputCaptcha)) {
                    throw new CaptchaException("user.error.login.captcha.null");
                }
                if (!StringUtils.equalsIgnoreCase(inputCaptcha, cacheCaptcha)) {
                    throw new CaptchaException("user.error.login.captcha.error");
                }
                authentication.setDetails(null);
            }
        }

        // 检查密码是否正确
        String password = userDetails.getPassword();
        String rawPassword = authentication.getCredentials().toString();

        boolean match = passwordEncoder.matches(rawPassword, password);
        if (!match) {
            throw new PasswordErrorException("user.error.login.username-or-password.error");
        }
    }
}
