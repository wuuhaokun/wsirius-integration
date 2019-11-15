package com.wsirius.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OAuth 属性配置
 * 
 * @author bojiangzhou 2018/11/03
 */
@ConfigurationProperties(prefix = "sunny.oauth")
public class OAuthProperties {


    /**
     * AccessToken 过期时间
     */
    private int accessTokenValiditySeconds = 2 * 3600;
    /**
     * RefreshToken 过期时间
     */
    private int refreshTokenValiditySeconds = 30 * 24 * 3600;

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public int getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }
}
