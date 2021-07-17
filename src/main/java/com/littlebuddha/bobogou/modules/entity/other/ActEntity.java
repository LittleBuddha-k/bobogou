package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;

import java.util.Date;

/**
 * 流程实体类
 */
public class ActEntity<T> extends DataEntity<T> {

    private String dataId;//执行数据id

    private Operator operator;//当前执行阶段人
    private String operatorId;//
    private String operatorName;//

    private Role role;//执行角色
    private String roleId;//
    private String roleName;//

    private String actStatus;//执行状态

    //private Operator creatBy;//发起人
    //private Date createDate;//发起时间
    //private Operator updateBy;//发起人
    //private Date updateDate;//发起时间

    public ActEntity() {
    }

    public ActEntity(String id) {
        super(id);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }
}
