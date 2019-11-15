package com.wsirius.security.domain.service;

import com.wsirius.core.base.Service;
import com.wsirius.security.domain.entity.Config;

/**
 * ConfigService
 *
 * @author bojiangzhou 2018/09/17
 */
public interface ConfigService extends Service<Config> {

    /**
     * @param passwordErrorTime 密码错误次数
     * @return 是否启用验证码
     */
    boolean isEnableCaptcha(Integer passwordErrorTime);
}
