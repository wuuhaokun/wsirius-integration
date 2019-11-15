package com.wsirius.security.social.wechat.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.wsirius.security.social.wechat.api.DefaultWechatApi;
import com.wsirius.security.social.wechat.api.WechatApi;

/**
 * 微信服务提供商
 *
 * @author bojiangzhou 2018/10/17
 */
public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<WechatApi> {
    /**
     * 获取授权码地址(引导用户跳转到这个地址上去授权)
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 获取令牌地址
     */
    private static final String URL_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WechatServiceProvider(String appId, String appSecret) {
        super(new WechatOauth2Template(appId, appSecret, URL_AUTHORIZE, URL_GET_ACCESS_TOKEN));
    }

    @Override
    public WechatApi getApi(String accessToken) {
        return new DefaultWechatApi(accessToken);
    }
}
