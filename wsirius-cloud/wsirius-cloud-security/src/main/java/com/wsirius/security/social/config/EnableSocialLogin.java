package com.wsirius.security.social.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 启用社交登录，同时需向 HttpSecurity 中加入 CustomSocialConfigurer 的配置.
 *
 * <pre>
 *     CustomSocialConfigurer socialConfigurer = new CustomSocialConfigurer();
 *     socialConfigurer
 *          .successHandler(socialAuthenticationSuccessHandler)
 *          .failureHandler(socialAuthenticationFailureHandler)
 *          .signupUrl(socialProperties.getSignupPage())
 *     ;
 *     http.apply(socialConfigurer);
 * <pre/>
 *
 * @author bojiangzhou 2018/10/19
 * @see CustomSocialConfigurer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SocialConfiguration.class)
public @interface EnableSocialLogin {

}
