package com.wsirius.rbac.security.controller;

import com.wsirius.core.base.Result;
import com.wsirius.core.util.Results;
import com.wsirius.rbac.security.domain.entity.VehicleInfo;
import com.wsirius.rbac.security.payload.CallTaxiRequest;
import com.wsirius.rbac.security.domain.service.VehicleInfoService;
import com.wsirius.rbac.security.util.DispatchCarUtil;
import com.wsirius.rbac.security.util.GeoUtils;
import com.wsirius.rbac.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
@RequestMapping("/api/v1/calltaxi")
public class CallTaxiController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @Autowired
    private JwtUtil jwtUtil;
//
//    @Autowired
//    private RabcUserUtil rabcUserUtil;


    @Autowired
    private GeoUtils geoUtils;

    @Autowired
    private DispatchCarUtil dispatchCarUtil;

    private
    double distance = 0;
    /**
     * 登录
     */


    @PostMapping("/usercall")
    public Result call(@Valid @RequestBody CallTaxiRequest callTaxiRequest) {
        List<VehicleInfo> vehicleInfoList = vehicleInfoService.selectAll();
        if(vehicleInfoList.size() <= 0){
            return Results.failure("附近沒有空車！！");
        }

        // List 自訂排序
        Collections.sort( vehicleInfoList, new Comparator<VehicleInfo>(){
            public int compare( VehicleInfo l1, VehicleInfo l2 ) {
                double distance1 = GeoUtils.getDistance(callTaxiRequest.getStartLatitude(), callTaxiRequest.getStartLongitude(), l1.getLatitude(), l1.getLongitude());
                double distance2 = GeoUtils.getDistance(callTaxiRequest.getStartLatitude(), callTaxiRequest.getStartLongitude(), l2.getLatitude(), l2.getLongitude());

                if (distance2 >  distance1){
                    if(distance < distance2){
                        distance = distance2;
                    }
                    return 1;
                }
                if(distance < distance1){
                    distance = distance1;
                }
                return -1;
            }
        });

        if(distance > 8.0) {
            return Results.failure("附近沒有空車！！");
        }
        Object[] objs = vehicleInfoList.toArray();
        VehicleInfo info = vehicleInfoList.get(0);

        return Results.success();
    }

}

//                let parameters = [ "Start_Address" : Start_Address , "Target_Address" : Target_Address , "Start_Latitude" : Start_Latitude , "Start_Longitude" : Start_Longitude , "Target_Latitude" : Target_Latitude , "Target_Longitude" : Target_Longitude , "Distance" : dou ] as [String : Any];
//                print("parameters:******\(parameters)")
//
//                var url: Array<String> = setUserCallTaxiAPI(); //回傳陣列