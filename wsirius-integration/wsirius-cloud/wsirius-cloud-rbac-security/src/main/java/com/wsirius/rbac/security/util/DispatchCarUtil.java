package com.wsirius.rbac.security.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.wsirius.rbac.security.domain.entity.*;
import com.wsirius.rbac.security.payload.RegisterRequest;
import com.wsirius.rbac.security.domain.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * JWT 工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: JWT 工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
//@EnableConfigurationProperties(JwtConfig.class)
//@Configuration
@Configuration
@Slf4j
public class DispatchCarUtil {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private User DispatchCar(RegisterRequest registerRequest, boolean isAdmin) {
        User user = new User();
        user.setId(snowflake.nextId());
        user.setUsername(registerRequest.getUsername());
        user.setNickname("普通用户");

        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setBirthday(DateTime.of(registerRequest.getBirthday(), "yyyy-MM-dd")
                .getTime());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setSex(registerRequest.getSex());
        user.setStatus(registerRequest.getStatus());
        user.setCreateTime(DateUtil.current(false));
        user.setUpdateTime(DateUtil.current(false));
        userService.insert(user);
        return user;
    }
}
