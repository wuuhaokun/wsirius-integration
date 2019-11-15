package com.wsirius.rbac.security.mapper;

import com.wsirius.core.base.Mapper;
import com.wsirius.rbac.security.entity.Role;
import com.wsirius.rbac.security.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * UserMapper
 *
 * @author bojiangzhou 2018/09/04
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> selectByUserId(Long userId);
}
