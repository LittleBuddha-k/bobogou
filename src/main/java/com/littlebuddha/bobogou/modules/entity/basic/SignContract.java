package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import org.apache.commons.lang3.StringUtils;

/**
 *合同签署实体类
 */
public class SignContract extends DataEntity<SignContract> {

    private Operator partA;//关联系统表经纪人、经理人
    private String partAId;//经纪人、经理人名字
    private String partAName = "";//经纪人、经理人名字
    private Operator partB;//关联系统表经纪人、经理人
    private String partBId;//乙方Id
    private String partBName = "";//乙方名字
    private String frontIdCard = "";//身份证正面
    private String backIdCard = "";//身份证反面
    private String qualification = "";//资质文件
    private String contract = "";//合同文件

    private String actType = "basic_sign_contract";//流程分类
    private Role role;//执行人角色Id
    private String nextRoleId;//执行人角色id
    private String roleName;//执行人角色名称
    private String status = "0";//审核状态----0：未提交 1：已提交 2：省级同意 3：省级同意 4：市级同意 5：市级拒绝 6：区级同意 7：区级拒绝 8：超级管理员助理同意 9：超级管理员助理拒绝 10：超级管理员同意 11：超级管理员拒绝

    private Role currentUserRole;//当前用户角色
    private String currentUserRoleId;//当前用户角色id

    public SignContract() {
    }

    public SignContract(String id) {
        super(id);
    }

    public Operator getPartA() {
        return partA;
    }

    public void setPartA(Operator partA) {
        this.partA = partA;
    }

    public String getPartAId() {
        return partAId;
    }

    public void setPartAId(String partAId) {
        this.partAId = partAId;
    }

    public String getPartAName() {
        return partAName;
    }

    public void setPartAName(String partAName) {
        this.partAName = partAName;
    }

    public Operator getPartB() {
        return partB;
    }

    public void setPartB(Operator partB) {
        this.partB = partB;
    }

    public String getPartBId() {
        return partBId;
    }

    public void setPartBId(String partBId) {
        this.partBId = partBId;
    }

    public String getPartBName() {
        return partBName;
    }

    public void setPartBName(String partBName) {
        this.partBName = partBName;
    }

    public String getFrontIdCard() {
        return frontIdCard;
    }

    public void setFrontIdCard(String frontIdCard) {
        this.frontIdCard = frontIdCard;
    }

    public String getBackIdCard() {
        return backIdCard;
    }

    public void setBackIdCard(String backIdCard) {
        this.backIdCard = backIdCard;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
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
        if(currentUserRole != null && StringUtils.isNotBlank(currentUserRole.getId())){
            currentUserRoleId = currentUserRole.getId();
        }
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(String currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }
}
