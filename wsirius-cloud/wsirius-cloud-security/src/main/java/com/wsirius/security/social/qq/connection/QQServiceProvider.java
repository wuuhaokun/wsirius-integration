package com.wsirius.security.social.qq.connection;

import com.wsirius.security.social.qq.api.DefaultQQApi;
import com.wsirius.security.social.qq.api.QQApi;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * QQ 服务提供商
 *
 * @author bojiangzhou 2018/10/17
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {
    /**
     * 获取授权码地址(引导用户跳转到这个地址上去授权)
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 获取令牌地址
     */
    private static final String URL_GET_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOauth2Template(appId, appSecret, URL_AUTHORIZE, URL_GET_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQApi getApi(String accessToken) {
        return new DefaultQQApi(accessToken, appId);
    }
}
