package com.wsirius.security.social.wechat.api;

/**
 * 微信 API
 *
 * @author bojiangzhou 2018/10/16
 */
public interface WechatApi {

    /**
     * 获取微信用户信息
     *
     * @param providerUserId 微信用户 openId
     */
    WechatUser getWechatUser(String providerUserId);

}
