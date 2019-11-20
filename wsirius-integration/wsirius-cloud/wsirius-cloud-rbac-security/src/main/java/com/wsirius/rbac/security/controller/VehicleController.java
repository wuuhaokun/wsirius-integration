package com.wsirius.rbac.security.controller;

import com.wsirius.core.base.Result;
import com.wsirius.core.util.Results;
import com.wsirius.rbac.security.domain.entity.VehicleInfo;
import com.wsirius.rbac.security.payload.VehicleInfoRequest;
import com.wsirius.rbac.security.domain.service.VehicleInfoService;
import com.wsirius.rbac.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 认证 Controller，包括用户注册，用户登录请求
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:23
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */

//暫沒用到的，不要移除的
//class LatLng {
//    /// The latitude in degrees between -90.0 and 90.0, both inclusive.
//    double latitude;
//    /// The longitude in degrees between -180.0 (inclusive) and 180.0 (exclusive).
//    double longitude;
//    LatLng(double latitude, double longitude){
//        latitude = latitude;
//        longitude = longitude;
//    }
//}

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 认证 Controller，包括用户注册，用户登录请求
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:23
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @Autowired
    private JwtUtil jwtUtil;
//
//    @Autowired
//    private RabcUserUtil rabcUserUtil;

    /**
     * 登录
     */
    @PostMapping("/register")
    public Result registerInfo(HttpServletRequest request, @Valid @RequestBody VehicleInfoRequest vehicleInfoRequest) {

//        public Result logout(HttpServletRequest request) {
//            try {
//                // 设置JWT过期
//                jwtUtil.invalidateJWT(request);
//            } catch (SecurityException e) {
//                throw new SecurityException(WStatus.UNAUTHORIZED);
//            }
//            //return ApiResponse.ofStatus(Status.LOGOUT);
//
//            return Results.success(WStatus.LOGOUT.toString(),"成功登出");
//
//        }
        String jwt = jwtUtil.getJwtFromRequest(request);
        Claims claims = jwtUtil.parseJWT(jwt);
        long id = Long.parseLong(claims.getId());;
        vehicleInfoRequest.setId(id);

        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setId(id);
        vehicleInfo.setLatitude(vehicleInfoRequest.getLatitude());
        vehicleInfo.setLongitude(vehicleInfoRequest.getLongitude());
        vehicleInfo.setStatus(vehicleInfoRequest.getStatus());

        vehicleInfoService.insert(vehicleInfo);
        //return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();

//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext()
//                .setAuthentication(authentication);
//        String jwt = jwtUtil.createJWT(authentication,loginRequest.getRememberMe());
        return Results.success();
    }

}
