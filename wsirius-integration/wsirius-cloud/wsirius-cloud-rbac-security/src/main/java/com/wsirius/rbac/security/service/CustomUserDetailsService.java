package com.wsirius.rbac.security.service;

import com.wsirius.rbac.security.domain.entity.Permission;
import com.wsirius.rbac.security.domain.entity.Role;
import com.wsirius.rbac.security.domain.entity.User;
import com.wsirius.rbac.security.domain.service.PermissionService;
import com.wsirius.rbac.security.domain.service.RoleService;
import com.wsirius.rbac.security.domain.service.UserService;
import com.wsirius.rbac.security.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 自定义UserDetails查询
 * </p>
 *
 * @package: com.xkcoding.rbac.security.service
 * @description: 自定义UserDetails查询
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 10:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    @Autowired
//    private PermissionDao permissionDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(usernameOrEmailOrPhone);
        //Kun 這里要修正
                //.orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        //List<Role> roles = roleDao.selectByUserId(user.getId());
        List<Role> roles = roleService.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        //List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        List<Permission> permissions = permissionService.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }
}
