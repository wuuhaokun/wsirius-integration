package com.wsirius.rbac.security.domain.mapper;

import com.wsirius.core.base.Mapper;
import com.wsirius.rbac.security.domain.entity.User;
import com.wsirius.rbac.security.domain.entity.VehicleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * UserMapper
 *
 * @author bojiangzhou 2018/09/04
 */
public interface VehicleInfoMapper extends Mapper<VehicleInfo> {

//    /**
//     * 通过用户名或手机号查询用户
//     *
//     * @param username 用户名或手机
//     * @return User
//     */

    User selectByUsername(@Param("username") String username);
    List<User> findByUsernameIn(List<String> usernameList);

}
