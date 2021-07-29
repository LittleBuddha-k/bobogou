package com.littlebuddha.bobogou.modules.entity.common;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;

import java.util.Date;

/**
 *审核历史记录实体类
 */
public class ActHistory extends DataEntity<ActHistory> {

    private String dataId;//流程数据id
    private String executionLink;//执行环节
    private Operator executionOperator;//执行人
    private String executionId;//执行人名字
    private String executionName;//执行人名字
    private Role role;//执行人角色Id
    private String roleId;//执行人角色Id
    private String roleName;//执行人角色名称
    private Date beginDate;//开始时间
    private Date endDate;//结束时间
    private String totalTime;//历时

    public ActHistory() {
    }

    public ActHistory(String id) {
        super(id);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getExecutionLink() {
        return executionLink;
    }

    public void setExecutionLink(String executionLink) {
        this.executionLink = executionLink;
    }

    public Operator getExecutionOperator() {
        return executionOperator;
    }

    public void setExecutionOperator(Operator executionOperator) {
        this.executionOperator = executionOperator;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getExecutionName() {
        return executionName;
    }

    public void setExecutionName(String executionName) {
        this.executionName = executionName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
