package com.wsirius.rbac.security.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wsirius.core.base.BaseService;
import com.wsirius.rbac.security.domain.entity.User;
import com.wsirius.rbac.security.domain.mapper.UserMapper;
import com.wsirius.rbac.security.domain.service.UserService;
import java.util.List;
/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> findByUsernameIn(List<String> usernameList){
        return userMapper.findByUsernameIn(usernameList);
    }

}
