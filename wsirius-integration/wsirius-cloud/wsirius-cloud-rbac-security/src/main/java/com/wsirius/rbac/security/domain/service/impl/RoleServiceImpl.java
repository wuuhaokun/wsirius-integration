package com.wsirius.rbac.security.domain.service.impl;

import com.wsirius.rbac.security.domain.entity.Role;
import com.wsirius.rbac.security.domain.mapper.RoleMapper;
import com.wsirius.rbac.security.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wsirius.core.base.BaseService;
import java.util.List;

/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectByUserId(Long userId){
        return roleMapper.selectByUserId(userId);
    }
//    public List<Role> selectByUserId(@Param("userId") Long userId){
//        return roleMapper.selectByUserId(userId);
//    }
}
