package com.wsirius.rbac.security.mapper;

import com.wsirius.core.base.Mapper;
import com.wsirius.rbac.security.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * UserMapper
 *
 * @author bojiangzhou 2018/09/04
 */
public interface UserMapper extends Mapper<User> {

//    /**
//     * 通过用户名或手机号查询用户
//     *
//     * @param username 用户名或手机
//     * @return User
//     */

    User selectByUsername(@Param("username") String username);

    //Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);
    //List<User> findByUsernameIn(List<String> usernameList);
}
