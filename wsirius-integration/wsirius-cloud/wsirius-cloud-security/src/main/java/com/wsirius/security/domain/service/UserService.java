package com.wsirius.security.domain.service;

import javax.servlet.http.HttpServletRequest;

import com.wsirius.core.base.Service;
import com.wsirius.security.domain.entity.User;

/**
 * User
 *
 * @author bojiangzhou 2018/09/06
 */
public interface UserService extends Service<User> {

    /**
     * 通过用户名或手机号查询用户
     * 
     * @param username 用户名或手机
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * 登录错误记录登录错误次数
     * 
     * @param userId 用户ID
     */
    void loginFail(Long userId);

    /**
     * 登录成功清除登录错误次数
     *
     * @param userId 用户ID
     */
    void loginSuccess(Long userId);

    /**
     * 绑定第三方站好
     */
    void bindProvider(String username, String password, HttpServletRequest request);
}
