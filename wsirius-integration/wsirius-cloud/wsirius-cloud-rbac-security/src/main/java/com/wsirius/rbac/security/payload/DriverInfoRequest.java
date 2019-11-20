package com.wsirius.rbac.security.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
public class DriverInfoRequest {

    /**
     * 主键
     */
    private Long id;
    /**
     * 經度
     */
    @NotBlank(message = "經度不能為空")
    private Double latitude;

    /**
     * 緯度
     */
    @NotBlank(message = "緯度不能為空")
    private Double longitude;

    /**
     * 手机
     */
    @NotBlank(message = "手机不能为空")
    private String phone;

    /**
     * 司機狀態
     */
    private Integer status = 0;

}
