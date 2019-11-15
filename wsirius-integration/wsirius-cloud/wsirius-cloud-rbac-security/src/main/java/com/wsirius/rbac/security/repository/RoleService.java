package com.wsirius.rbac.security.repository;

import com.wsirius.core.base.Service;
import com.wsirius.rbac.security.entity.Role;

import java.util.List;
import java.util.Optional;

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
public interface RoleService extends Service<Role> {
    //List<Role> selectByUserId(@Param("userId") Long userId);
    List<Role> selectByUserId(Long userId);
}
