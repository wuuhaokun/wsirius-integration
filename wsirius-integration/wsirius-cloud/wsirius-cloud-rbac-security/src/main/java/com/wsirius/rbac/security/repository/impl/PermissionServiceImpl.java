package com.wsirius.rbac.security.repository.impl;

import javax.servlet.http.HttpServletRequest;
//
//import com.wsirius.core.exception.CommonException;
import com.wsirius.rbac.security.entity.Permission;
//import com.wsirius.rbac.security.mapper.PermissionMapper;
import com.wsirius.rbac.security.mapper.PermissionMapper;
import com.wsirius.rbac.security.repository.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wsirius.core.base.BaseService;
import com.wsirius.rbac.security.entity.User;
import com.wsirius.rbac.security.mapper.UserMapper;
import com.wsirius.rbac.security.repository.UserService;
import org.springframework.web.context.request.ServletWebRequest;
import com.wsirius.core.base.BaseService;

import java.util.List;
import java.util.Optional;

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
