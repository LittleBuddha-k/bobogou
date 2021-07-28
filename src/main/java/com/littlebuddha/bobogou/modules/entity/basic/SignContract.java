package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;

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
    private String status = "0";//审核状态

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
