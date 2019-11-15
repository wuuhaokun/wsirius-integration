package com.wsirius.core.base;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.wsirius.core.util.Dates;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import tk.mybatis.mapper.annotation.Version;

/**
 * 基础实体类. 统一定义创建时间，版本等.
 *
 * @author bojiangzhou 2017-12-29
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6308631723696535907L;

    public static final String FIELD_ID = "id";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_CREATE_DATE = "createDate";
    public static final String FIELD_UPDATE_DATE = "updateDate";
    public static final String FIELD_CREATE_BY = "createBy";
    public static final String FIELD_UPDATE_BY = "updateBy";

    /**
     * 数据版本号,每发生update则自增,用于实现乐观锁.
     */
    @Version
    protected Long version;

    /**
     * 创建时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = Dates.DEFAULT_PATTERN)
    @Column(updatable = false)
    protected Date createDate;

    /**
     * 更新时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = Dates.DEFAULT_PATTERN)
    protected Date updateDate;

    /**
     * 创建人
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long createBy;

    /**
     * 更新人
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long updateBy;

    /**
     * 其它属性
     */
    @JsonIgnore
    @Transient
    protected Map<String, Object> innerMap = new HashMap<>();


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    @JsonAnyGetter
    public Object getAttribute(String key) {
        return innerMap.get(key);
    }

    @JsonAnySetter
    public void setAttribute(String key, Object obj) {
        innerMap.put(key, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String toJSONString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
