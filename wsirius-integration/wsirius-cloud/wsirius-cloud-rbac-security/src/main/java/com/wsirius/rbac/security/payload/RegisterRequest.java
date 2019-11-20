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
public class RegisterRequest {

    /**
     * 主键
     */
//    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    @NotBlank(message = "手机不能为空")
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别，男-1，女-2
     */
    private Integer sex = 0;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status = 1;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
