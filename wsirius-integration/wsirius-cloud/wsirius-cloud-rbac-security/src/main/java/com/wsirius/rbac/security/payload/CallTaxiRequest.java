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
public class  CallTaxiRequest {

    /**
     * 主键
     */
    private Long id;
    /**
     * 經度
     */
    //@NotNull(message = "經度不能為空")
    private Double startLatitude;

    /**
     * 緯度
     */
    //@NotNull(message = "緯度不能為空")
    private Double startLongitude;

    //@NotNull(message = "經度不能為空")
    private Double endLatitude;

    /**
     * 緯度
     */
    //@NotNull(message = "緯度不能為空")
    private Double endLongitude;

    /**
     * 手机
     */
    private double distance;

}