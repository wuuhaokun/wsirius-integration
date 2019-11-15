package com.wsirius.security.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.wsirius.captcha.CaptchaMessageHelper;
import com.wsirius.security.core.CustomUserDetailsService;
import com.wsirius.security.sms.SmsAuthenticationDetailsSource;
import com.wsirius.security.sms.SmsAuthenticationFailureHandler;
import com.wsirius.security.sms.SmsAuthenticationProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 短信登录配置
 *
 * @author bojiangzhou 2018/10/28
 */
@Configuration
public class SmsLoginConfiguration {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CaptchaMessageHelper captchaMessageHelper;

    @Bean
    public SmsAuthenticationFailureHandler smsAuthenticationFailureHandler() {
        return new SmsAuthenticationFailureHandler();
    }

    @Bean
    public SmsAuthenticationDetailsSource smsAuthenticationDetailsSource() {
        return new SmsAuthenticationDetailsSource();
    }

    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        return new SmsAuthenticationProvider(userDetailsService, captchaMessageHelper);
    }

}
