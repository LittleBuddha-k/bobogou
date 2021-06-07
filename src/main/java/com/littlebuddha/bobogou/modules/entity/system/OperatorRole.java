package com.littlebuddha.bobogou.modules.entity.system;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * operator插入role时的工具实体类
 */
public class OperatorRole extends DataEntity<OperatorRole> {

    private Operator operator;
    private Role role;

    public OperatorRole() {
    }

    public OperatorRole(Operator operator) {
        this.operator = operator;
    }

    public OperatorRole(Operator operator, Role role) {
        this.operator = operator;
        this.role = role;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
