package com.wsirius.rbac.security.domain.service.impl;

import com.wsirius.rbac.security.domain.entity.Permission;
import com.wsirius.rbac.security.domain.mapper.PermissionMapper;
import com.wsirius.rbac.security.domain.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wsirius.core.base.BaseService;
import java.util.List;

/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class PermissionServiceImpl extends BaseService<Permission> implements PermissionService {

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    public List<Permission> selectByRoleIdList(List<Long> ids){
        return permissionMapper.selectByRoleIdList(ids);
    }

}
