package com.wsirius.security.social.qq.connection;

import com.wsirius.security.social.qq.api.QQApi;
import com.wsirius.security.social.qq.api.QQUser;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQApi 适配器
 *
 * @author bojiangzhou 2018/10/17
 */
public class QQApiAdapter implements ApiAdapter<QQApi> {

    /**
     * 测试Api连接是否可用
     * 
     * @param api QQApi
     */
    @Override
    public boolean test(QQApi api) {
        return true;
    }

    /**
     * QQApi 与 Connection 做适配
     * @param api QQApi
     * @param values Connection
     */
    @Override
    public void setConnectionValues(QQApi api, ConnectionValues values) {
        QQUser user = api.getQQUser();

        values.setDisplayName(user.getNickname());
        values.setImageUrl(user.getFigureurl());
        values.setProviderUserId(user.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQApi api) {
        return null;
    }

    @Override
    public void updateStatus(QQApi api, String message) {

    }
}
