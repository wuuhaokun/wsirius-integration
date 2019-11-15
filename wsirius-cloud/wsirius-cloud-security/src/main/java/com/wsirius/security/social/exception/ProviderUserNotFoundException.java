package com.wsirius.security.social.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 服务提供商处用户不存在
 *
 * @author bojiangzhou 2018/10/28
 */
public class ProviderUserNotFoundException extends AuthenticationException {

    public ProviderUserNotFoundException(String msg) {
        super(msg);
    }

    public ProviderUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
