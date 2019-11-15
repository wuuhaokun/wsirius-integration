package com.wsirius.security.social.wechat.api;

import java.util.List;

import com.google.common.base.Charsets;
import com.wsirius.security.social.exception.ProviderUserNotFoundException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 微信 API 默认实现
 *
 * @author bojiangzhou 2018/10/16
 */
public class DefaultWechatApi extends AbstractOAuth2ApiBinding implements WechatApi {

    /**
     * 微信获取用户信息的地址
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid={openId}";

    public DefaultWechatApi(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WechatUser getWechatUser(String providerUserId) {
        WechatUser user = getRestTemplate().getForObject(URL_GET_USER_INFO, WechatUser.class, providerUserId);
        if (user == null) {
            throw new ProviderUserNotFoundException("user.error.login.provider.user.not-found");
        }
        return user;
    }

    /**
     * 由于默认添加的 StringHttpMessageConverter 编码使用 {@code "ISO-8859-1"}，而微信要求 {@code "UTF-8"}，
     * 因此需要重新添加 UTF-8 的 StringHttpMessageConverter
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = super.getMessageConverters();
        converters.remove(0);
        converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
        return converters;
    }

}
