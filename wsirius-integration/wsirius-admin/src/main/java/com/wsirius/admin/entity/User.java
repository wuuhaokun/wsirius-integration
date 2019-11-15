package com.wsirius.admin.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wsirius.core.base.BaseEntity;

/**
 * 系统用户
 *
 * @author bojiangzhou 2018/09/04
 */
@Table(name = "sys_user")
public class User extends BaseEntity {



    //
    // 实体字段
    // ------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
    /**
     * 用户名 登录账号(唯一)
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称 真实姓名
     */
    private String nickname;
    /**
     * 手机号(唯一)
     */
    private String mobile;
    /**
     * 邮箱(唯一)
     */
    private String email;
    /**
     * 语言
     */
    private String language;
    /**
     * 是否超级管理员
     */
    private Boolean supperAdmin;
    /**
     * 是否锁定
     */
    private Boolean locked;
    /**
     * 锁定日期
     */
    private Date lockedDate;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 禁用日期
     */
    private Date disabledDate;
    /**
     * 头像地址
     */
    private String photoUrl;
    /**
     * 最后登录日期
     */
    private Date lastLoginDate;
    /**
     * 密码错误次数
     */
    private Integer passwordErrorTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getSupperAdmin() {
        return supperAdmin;
    }

    public void setSupperAdmin(Boolean supperAdmin) {
        this.supperAdmin = supperAdmin;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getPasswordErrorTime() {
        return passwordErrorTime;
    }

    public void setPasswordErrorTime(Integer passwordErrorTime) {
        this.passwordErrorTime = passwordErrorTime;
    }

}
