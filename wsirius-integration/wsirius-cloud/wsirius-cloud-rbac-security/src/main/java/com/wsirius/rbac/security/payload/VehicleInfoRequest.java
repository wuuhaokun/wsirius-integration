package com.wsirius.rbac.security.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 登录请求参数
 * </p>
 *
 * @package: com.xkcoding.rbac.security.payload
 * @description: 登录请求参数
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 15:52
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class  VehicleInfoRequest {

    /**
     * 主键
     */
    private Long id;
    /**
     * 經度
     */
//    @NotBlank(message = "經度不能為空")
    //@Column(name = "salary", nullable = false)
    @NotNull(message= "經度不能為空")
    //@Range(min = 1)
    private Double latitude;

    /**
     * 緯度
     */
    //@NotBlank(message = "緯度不能為空")
    @NotNull(message= "緯度不能為空")
    private Double longitude;

//    /**
//     * 手机
//     */
//    @NotBlank(message = "手机不能为空")
//    private String phone;

    /**
     * 司機狀態
     */
    private Integer status = 0;

}

//api/CallTaxi/UserCall

//                let parameters = [ "Start_Address" : Start_Address , "Target_Address" : Target_Address , "Start_Latitude" : Start_Latitude , "Start_Longitude" : Start_Longitude , "Target_Latitude" : Target_Latitude , "Target_Longitude" : Target_Longitude , "Distance" : dou ] as [String : Any];
//                print("parameters:******\(parameters)")
//
//                var url: Array<String> = setUserCallTaxiAPI(); //回傳陣列

