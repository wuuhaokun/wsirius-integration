package com.wsirius.security.domain.mapper;

import com.wsirius.core.base.Mapper;
import com.wsirius.security.domain.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 *
 * @author bojiangzhou 2018/09/04
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 通过用户名或手机号查询用户
     *
     * @param username 用户名或手机
     * @return User
     */
    User selectByUsername(@Param("username") String username);

}
