package com.wsirius.security.social.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户未绑定异常
 *
 * @author bojiangzhou 2018/10/28
 */
public class UserNotBindException extends AuthenticationException {

    public UserNotBindException(String msg) {
        super(msg);
    }

    public UserNotBindException(String msg, Throwable t) {
        super(msg, t);
    }
}
