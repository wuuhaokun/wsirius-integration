package com.wsirius.security.core;

import java.util.ArrayList;
import java.util.Collection;

import com.wsirius.core.userdetails.CustomUserDetails;
import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.UserService;
import com.wsirius.security.exception.AccountNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 加载用户信息实现类
 *
 * @author bojiangzhou 2018/03/25
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws AccountNotExistsException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new AccountNotExistsException("user.error.login.username-or-password.error");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getUserId(),
                user.getNickname(), user.getLanguage(), authorities);
    }

}
