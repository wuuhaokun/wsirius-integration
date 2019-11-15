package com.wsirius.security.social.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * social 三方配置
 *
 * @author bojiangzhou 2018/10/18
 */
@ConfigurationProperties(prefix = SocialProperties.PREFIX)
public class SocialProperties {

    public static final String PREFIX = "sunny.social";


    private Qq qq = new Qq();

    private Wechat wechat = new Wechat();

    /**
     * Social 注册或绑定页面
     */
    private String signupPage = "/signup";


    /**
     * QQ 配置
     */
    public static class Qq {
        /**
         * 服务商ID
         */
        private String providerId = "qq";
        /**
         * appId
         */
        private String appId;
        /**
         * appSecret
         */
        private String appSecret;

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }
    }

    /**
     * 微信 配置
     */
    public static class Wechat {
        /**
         * 服务商ID
         */
        private String providerId = "wechat";
        /**
         * appId
         */
        private String appId;
        /**
         * appSecret
         */
        private String appSecret;

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }
    }

    public Qq getQq() {
        return qq;
    }

    public void setQq(Qq qq) {
        this.qq = qq;
    }

    public Wechat getWechat() {
        return wechat;
    }

    public void setWechat(Wechat wechat) {
        this.wechat = wechat;
    }

    public String getSignupPage() {
        return signupPage;
    }

    public void setSignupPage(String signupPage) {
        this.signupPage = signupPage;
    }
}
