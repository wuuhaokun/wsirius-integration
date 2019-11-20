package com.wsirius.security.domain.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.wsirius.core.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wsirius.core.base.BaseService;
import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.mapper.UserMapper;
import com.wsirius.security.domain.service.UserService;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loginFail(Long userId) {
        User user = select(userId);
        user.loginFail();

        update(user);
    }

    @Override
    public void loginSuccess(Long userId) {
        User user = select(userId);
        user.loginSuccess();

        update(user);
    }

    @Override
    public void bindProvider(String username, String password, HttpServletRequest request) {
        // login
        User user = select(User.FIELD_USERNAME, username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new CommonException("user.error.login.username-or-password.error");
        }

        providerSignInUtils.doPostSignUp(user.getUserId().toString(), new ServletWebRequest(request));
    }

}
