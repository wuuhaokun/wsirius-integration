package com.wsirius.security.config;

import com.wsirius.security.core.CustomAuthenticationDetailsSource;
import com.wsirius.security.core.CustomAuthenticationFailureHandler;
import com.wsirius.security.core.CustomAuthenticationSuccessHandler;
import com.wsirius.security.core.CustomLogoutSuccessHandler;
import com.wsirius.security.sms.SmsAuthenticationDetailsSource;
import com.wsirius.security.sms.SmsAuthenticationFailureHandler;
import com.wsirius.security.sms.SmsAuthenticationProvider;
import com.wsirius.security.sms.config.EnableSmsLogin;
import com.wsirius.security.sms.config.SmsLoginConfigurer;
import com.wsirius.security.social.config.CustomSocialConfigurer;
import com.wsirius.security.social.config.EnableSocialLogin;
import com.wsirius.security.social.config.SocialProperties;
import com.wsirius.security.social.core.CustomSocialAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security 主配置器
 *
 * @author bojiangzhou
 */
@Configuration
@EnableSocialLogin
@EnableSmsLogin
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties properties;
    @Autowired
    private CustomAuthenticationDetailsSource authenticationDetailsSource;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Autowired(required = false)
    private SmsAuthenticationFailureHandler smsAuthenticationFailureHandler;
    @Autowired(required = false)
    private SmsAuthenticationDetailsSource smsAuthenticationDetailsSource;
    @Autowired(required = false)
    private SmsAuthenticationProvider smsAuthenticationProvider;

    @Autowired(required = false)
    private CustomSocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler;

    @Autowired
    private SocialProperties socialProperties;

    @Override
    @SuppressWarnings("unchecked")
    protected void configure(HttpSecurity http) throws Exception {
        // 标准登录配置
        http
            .authorizeRequests()
                .antMatchers(
                        "/static/**",
                        "/webjars/**",
                        "/public/**",
                        "/favicon.ico",
                        "/login",
                        "/register",
                        "/bind",
                        socialProperties.getSignupPage(),
                        "/authentication/**",
                        "/*.html",
                        "/actuator/**"
                )
                .permitAll() // 允许匿名访问的地址
                .and() // 使用and()方法相当于XML标签的关闭，这样允许我们继续配置父类节点。
            .authorizeRequests()
                .anyRequest()
                .authenticated() // 其它地址都需进行认证
                .and()
            .formLogin() // 启用表单登录
                .loginPage(properties.getLoginPage()) // 登录页面
                .defaultSuccessUrl("/") // 默认的登录成功后的跳转地址
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .logout()
                .logoutSuccessHandler(logoutSuccessHandler) // 退出成功处理器，不能和 logoutSuccessUrl 同时使用
                .logoutSuccessUrl("/") // 退出成功跳到首页
                .deleteCookies("JSESSIONID", "SESSION") // 删除 Cookie
                .and()
            .sessionManagement()
                .invalidSessionUrl(properties.getLoginPage()) // session 失效后跳转的页面
                //.maximumSessions(1) // 设置同一用户最多存在几个 Session，后登陆的用户将踢掉前面的用户
                //.maxSessionsPreventsLogin(true) // 当 Session 超出时，阻止后面的登录
                //.expiredSessionStrategy(new CustomSessionInformationExpiredStrategy()) // 用户失效处理
                //.and()
                .and()
            .csrf()
            .disable()
        ;

        // 配置短信登录
        SmsLoginConfigurer smsLoginConfigurer = new SmsLoginConfigurer();
        smsLoginConfigurer
                .authenticationDetailsSource(smsAuthenticationDetailsSource)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(smsAuthenticationFailureHandler)
        ;
        http.apply(smsLoginConfigurer);
        http.authenticationProvider(smsAuthenticationProvider);

        // 配置社交登录
        CustomSocialConfigurer socialConfigurer = new CustomSocialConfigurer();
        socialConfigurer
                .successHandler(socialAuthenticationSuccessHandler)
                .signupUrl(socialProperties.getSignupPage())
        ;
        http.apply(socialConfigurer);
    }

}
