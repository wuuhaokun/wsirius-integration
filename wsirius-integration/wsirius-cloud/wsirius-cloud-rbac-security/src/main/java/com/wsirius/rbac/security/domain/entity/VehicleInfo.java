package com.wsirius.rbac.security.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 角色
 * </p>
 *
 * @package: com.xkcoding.rbac.security.model
 * @description: 角色
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 15:45
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Entity
@Table(name = "Vehicle_Info")
public class VehicleInfo {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 角色名
     */
    private  Double latitude;

    private Double longitude;

    private Integer status;

//    /**
//     * 创建时间
//     */
//    @Column(name = "create_time")
//    private Long createTime;
//
//    /**
//     * 更新时间
//     */
//    @Column(name = "update_time")
//    private Long updateTime;

}