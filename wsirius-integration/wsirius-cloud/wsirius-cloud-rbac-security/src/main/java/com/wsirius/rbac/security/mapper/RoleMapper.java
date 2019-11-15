package com.wsirius.rbac.security.mapper;

import com.wsirius.core.base.Mapper;
import com.wsirius.rbac.security.entity.Role;
import java.util.List;

/**
 * UserMapper
 *
 * @author bojiangzhou 2018/09/04
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> selectByUserId(Long userId);
}
