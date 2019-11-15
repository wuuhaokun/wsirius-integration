package com.wsirius.security.sms;

import com.wsirius.captcha.CaptchaMessageHelper;
import com.wsirius.captcha.CaptchaResult;
import com.wsirius.security.constant.SecurityConstants;
import com.wsirius.security.exception.CaptchaException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * 短信登录认证器
 * <p>
 * 参考 {@link AbstractUserDetailsAuthenticationProvider}，{@link DaoAuthenticationProvider}
 *
 * @author bojiangzhou 2018/09/22
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private CaptchaMessageHelper captchaMessageHelper;

    public SmsAuthenticationProvider(UserDetailsService userDetailsService, CaptchaMessageHelper captchaMessageHelper) {
        this.userDetailsService = userDetailsService;
        this.captchaMessageHelper = captchaMessageHelper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SmsAuthenticationToken.class, authentication,
                        "Only SmsAuthenticationToken is supported");

        String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

        UserDetails user = retrieveUser(mobile, (SmsAuthenticationToken) authentication);
        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

        additionalAuthenticationChecks(user, (SmsAuthenticationToken) authentication);

        return createSuccessAuthentication(user, authentication, user);
    }

    protected UserDetails retrieveUser(String mobile, SmsAuthenticationToken authentication)
                    throws AuthenticationException {

        return getUserDetailsService().loadUserByUsername(mobile);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, SmsAuthenticationToken authentication)
                    throws AuthenticationException {
        Assert.isInstanceOf(SmsAuthenticationDetails.class, authentication.getDetails());
        SmsAuthenticationDetails details = (SmsAuthenticationDetails) authentication.getDetails();
        String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        // 检查验证码
        String inputCaptcha = details.getInputCaptcha();
        String captchaKey = details.getCaptchaKey();
        if (StringUtils.isAnyEmpty(inputCaptcha, captchaKey)) {
            throw new CaptchaException("user.error.login.mobile-captcha.null");
        }
        CaptchaResult captchaResult = captchaMessageHelper.checkCaptcha(captchaKey, inputCaptcha, mobile,
                        SecurityConstants.SECURITY_KEY, false);
        authentication.setDetails(null);

        if (!captchaResult.isSuccess()) {
            throw new CaptchaException(captchaResult.getMessage());
        }
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                    UserDetails user) {
        SmsAuthenticationToken result =
                        new SmsAuthenticationToken(principal, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }

    /**
     * 只有 {@link SmsAuthenticationToken} 类型才使用该认证器
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public CaptchaMessageHelper getCaptchaMessageHelper() {
        return captchaMessageHelper;
    }

}
