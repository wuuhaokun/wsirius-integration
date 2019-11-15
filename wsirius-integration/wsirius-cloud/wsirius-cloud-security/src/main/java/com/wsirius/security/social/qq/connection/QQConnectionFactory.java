package com.wsirius.security.social.qq.connection;

import com.wsirius.security.social.qq.api.QQApi;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ Connection 工厂
 *
 * @author bojiangzhou 2018/10/17
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {


    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQApiAdapter());
    }
}
