package com.wsirius.security.social.core;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.UserService;
import com.wsirius.security.exception.AccountNotExistsException;

/**
 * 定制 Social UserDetailsService 用于获取系统用户信息
 *
 * @author bojiangzhou 2018/10/17
 */
public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userService.select(Long.valueOf(userId));

        if (user == null) {
            throw new AccountNotExistsException("user.error.login.username-or-password.error");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomSocialUserDetails(user.getUsername(), user.getPassword(), userId, user.getNickname(),
                        user.getLanguage(), authorities);
    }
}
