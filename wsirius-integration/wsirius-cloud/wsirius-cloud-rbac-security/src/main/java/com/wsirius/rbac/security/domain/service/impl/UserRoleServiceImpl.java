package com.wsirius.rbac.security.domain.service.impl;

import com.wsirius.rbac.security.domain.entity.UserRole;
import com.wsirius.rbac.security.domain.service.UserRoleService;
import org.springframework.stereotype.Service;
import com.wsirius.core.base.BaseService;


/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {
//    @Autowired(required = false)
//    private UserRoleMapper userRoleMapper;
//
    //@Override
    public int insertUserRole(UserRole userRole) {
        //return userRoleMapper.insertUserRole(userRole);
        return 0;
    }

//    @Override
//    public UserRole insert(UserRole userRole){
//        userRoleMapper.insert(userRole);
//        return null;
//    }

}
