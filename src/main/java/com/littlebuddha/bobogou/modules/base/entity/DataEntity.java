package com.littlebuddha.bobogou.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    protected String isDeleted = "0";       //0---正常  1---删除
    protected String delFlag = "0";       //0---正常  1---删除  初始值为0

    protected Operator createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;
    protected Operator updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        if (!this.isNewRecord){
            if(this.getIdType().equals(IDTYPE_UUID)){
                setId(AutoId.getAutoId());
            }else if(this.getIdType().equals(IDTYPE_AUTO)){
                //使用自增长不需要设置主键
            }

        }
        Subject subject = SecurityUtils.getSubject();
        Operator entity = (Operator) subject.getPrincipal();
        Date now = new Date();
        this.updateTime = now;
        this.updateDate = now;
        this.createBy = entity;
        this.createDate = this.updateDate;
        this.createTime = this.updateTime;
        this.updateBy = entity;
    }

    /**
     * 更新数据前的操作，手动调用
     */
    public void preUpdate() {
        Subject subject = SecurityUtils.getSubject();
        Operator entity = (Operator) subject.getPrincipal();
        this.updateBy = entity;
        this.updateTime = new Date();
        this.updateDate = new Date();
    }

    public boolean getNewData() {
        return isNewData;
    }

    public void setNewData(boolean newData) {
        isNewData = newData;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Operator getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Operator createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Operator getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Operator updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
