package com.wsirius.common;

/**
 * 全局配置常量
 *
 * @author bojiangzhou 2018/09/17
 */
public enum ConfigEnum {

    /**
     * 密码输错超过次数后锁定用户 [<= 0 则不锁定用户]
     */
    PASSWORD_ERROR_TIME_TO_LOCK_USER,
    /**
     * 面膜输错超过次数后启用验证码 [<= 0 则不启用验证码]
     */
    PASSWORD_ERROR_TIME_TO_ENABLE_CAPTCHA,


}
