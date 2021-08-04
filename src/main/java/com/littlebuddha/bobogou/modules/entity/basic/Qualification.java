package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import org.apache.commons.lang3.StringUtils;

/**
 *公司资质实体类
 */
public class Qualification extends DataEntity<Qualification> {

    private String name = "";//名称
    private String qualification = "";//资质图片

    private String actType = "basic_qualification";//流程类型
    private Role role;//执行人角色Id
    private String nextRoleId;//执行人角色id
    private String roleName;//执行人角色名称
    private String status = "0";//审核状态 审核状态----0：未提交 1：已提交 2：省级同意 3：省级同意 4：市级同意 5：市级拒绝 6：区级同意 7：区级拒绝 8：超级管理员助理同意 9：超级管理员助理拒绝 10：超级管理员同意 11：超级管理员拒绝

    private Role currentUserRole;//当前用户角色
    private String currentUserRoleId;//当前用户角色id

    public Qualification() {
    }

    public Qualification(String id) {
        super(id);
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNextRoleId() {
        return nextRoleId;
    }

    public void setNextRoleId(String nextRoleId) {
        this.nextRoleId = nextRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(Role currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public String getCurrentUserRoleId() {
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(String currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }
}
