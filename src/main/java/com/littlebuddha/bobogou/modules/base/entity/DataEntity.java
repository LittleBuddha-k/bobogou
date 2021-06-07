package com.littlebuddha.bobogou.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlebuddha.bobogou.common.utils.AutoId;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Date;

/**
 * 数据实体继承类
 */
public abstract class DataEntity<E> extends BaseEntity<E> {

    private boolean isNewData = false;//此项数据是否为新数据

    protected String isDeleted;       //0---正常  1---删除

    protected Operator createBy;
    protected Date createTime;
    protected Operator updateBy;
    protected Date updateTime;

    protected String remarks;

    public DataEntity() {
        super();
        this.isDeleted = DEL_FLAG_NORMAL;
    }

    public DataEntity(String id) {
        super(id);
    }

    @JsonIgnore
    public boolean getIsNewData() {
        return isNewData || StringUtils.isBlank(getId());
    }

    /**
     * 保存数据前的操作，手动调用
     */
    public void preInsert() {
        Subject subject = SecurityUtils.getSubject();
        Operator entity = (Operator) subject.getPrincipal();
        if (!this.isNewData) {
            setId(AutoId.getAutoId());
        }
        Date now = new Date();
        this.createBy = entity;
        this.createTime = now;
        this.updateBy = entity;
        this.updateTime = this.updateTime;
    }

    /**
     * 更新数据前的操作，手动调用
     */
    public void preUpdate() {
        Subject subject = SecurityUtils.getSubject();
        Operator entity = (Operator) subject.getPrincipal();
        this.updateBy = entity;
        this.updateTime = new Date();
    }

    public Operator getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Operator createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createTime;
    }

    public void setCreateDate(Date createDate) {
        this.createTime = createDate;
    }

    public Operator getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Operator updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateTime;
    }

    public void setUpdateDate(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return isDeleted;
    }

    public void setDelFlag(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
