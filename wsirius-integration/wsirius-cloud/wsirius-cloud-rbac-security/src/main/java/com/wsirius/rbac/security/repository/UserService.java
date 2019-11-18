package com.wsirius.rbac.security.repository;

import com.wsirius.core.base.Service;
import com.wsirius.rbac.security.entity.User;

import java.util.List;

/**
 * <p>
 * 用户 DAO
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 用户 DAO
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserService extends Service<User> {
    /**
     * 通过用户名或手机号查询用户
     *
     * @param username 用户名或手机
     * @return User
     */
    User getUserByUsername(String username);
    List<User> findByUsernameIn(List<String> usernameList);

}
