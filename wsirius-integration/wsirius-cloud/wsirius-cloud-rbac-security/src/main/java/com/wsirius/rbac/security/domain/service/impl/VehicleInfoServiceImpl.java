package com.wsirius.rbac.security.domain.service.impl;

import com.wsirius.rbac.security.domain.entity.VehicleInfo;
import com.wsirius.rbac.security.domain.mapper.VehicleInfoMapper;
import com.wsirius.rbac.security.domain.service.VehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsirius.core.base.BaseService;

/**
 *
 * @author bojiangzhou 2018/09/04
 */
@Service
public class VehicleInfoServiceImpl extends BaseService<VehicleInfo> implements VehicleInfoService {
    @Autowired(required = false)
    private VehicleInfoMapper vehicleInforMapper;

//    @Override
//    public User getUserByUsername(String username) {
//        return userMapper.selectByUsername(username);
//    }
//
//    @Override
//    public List<User> findByUsernameIn(List<String> usernameList){
//        return userMapper.findByUsernameIn(usernameList);
//    }

}
