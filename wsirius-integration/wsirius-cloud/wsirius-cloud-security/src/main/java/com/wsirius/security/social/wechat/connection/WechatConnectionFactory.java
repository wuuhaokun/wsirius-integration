package com.wsirius.security.social.wechat.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.wsirius.security.social.wechat.api.WechatApi;

/**
 * 微信 Connection 工厂
 *
 * @author bojiangzhou 2018/10/17
 */
public class WechatConnectionFactory extends OAuth2ConnectionFactory<WechatApi> {

    public WechatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WechatServiceProvider(appId, appSecret), new WechatApiAdapter());
        // 微信默认 scope=snsapi_login
        setScope("snsapi_login");
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WechatAccessGrant) {
            WechatAccessGrant wechatAccessGrant = (WechatAccessGrant) accessGrant;
            return wechatAccessGrant.getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WechatApi> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<WechatApi> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private OAuth2ServiceProvider<WechatApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WechatApi>) getServiceProvider();
    }

    private ApiAdapter<WechatApi> getApiAdapter(String providerUserId) {
        return new WechatApiAdapter(providerUserId);
    }
}
