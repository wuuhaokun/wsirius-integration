package com.wsirius.rbac.security.domain.service;

import com.wsirius.core.base.Service;
import com.wsirius.rbac.security.domain.entity.UserRole;


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
public interface UserRoleService extends Service<UserRole> {

    //UserRole getUserByUsername(long userId);
    //UserRole insert(UserRole userRole);

    //UserRole insert(UserRole userRole);
    int insertUserRole(UserRole userRole);
}
