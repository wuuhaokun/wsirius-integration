package com.wsirius.security.social.wechat.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.wsirius.security.social.wechat.api.WechatApi;
import com.wsirius.security.social.wechat.api.WechatUser;

/**
 * WechatApi 适配器
 *
 * @author bojiangzhou 2018/10/17
 */
public class WechatApiAdapter implements ApiAdapter<WechatApi> {

    private String providerUserId;

    public WechatApiAdapter() {
    }

    public WechatApiAdapter(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    /**
     * 测试Api连接是否可用
     * 
     * @param api WechatApi
     */
    @Override
    public boolean test(WechatApi api) {
        return true;
    }

    /**
     * WechatApi 与 Connection 做适配
     * @param api WechatApi
     * @param values Connection
     */
    @Override
    public void setConnectionValues(WechatApi api, ConnectionValues values) {
        WechatUser user = api.getWechatUser(providerUserId);

        values.setDisplayName(user.getNickname());
        values.setImageUrl(user.getHeadimgurl());
        values.setProviderUserId(user.getOpenid());
    }

    @Override
    public UserProfile fetchUserProfile(WechatApi api) {
        return null;
    }

    @Override
    public void updateStatus(WechatApi api, String message) {

    }
}
