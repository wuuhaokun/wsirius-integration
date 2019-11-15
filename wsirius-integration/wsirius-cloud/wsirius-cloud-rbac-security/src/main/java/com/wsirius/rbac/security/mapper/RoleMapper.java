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
    //@Query(value = "SELECT sec_role.* FROM sec_role,sec_user,sec_user_role WHERE sec_user.id = sec_user_role.user_id AND sec_role.id = sec_user_role.role_id AND sec_user.id = :userId", nativeQuery = true)
    //List<Role> selectByUserId(@Param("userId") Long userId);
    List<Role> selectByUserId(Long userId);
}
