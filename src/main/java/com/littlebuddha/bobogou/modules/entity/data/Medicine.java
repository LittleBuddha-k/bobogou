package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 药品实体类
 */
public class Medicine extends DataEntity<Medicine> {

    private String name;            //药名
    private String function;        //功能
    private Double originalPrice;   //原价
    private Double discountPrice;   //折后价
    private String picture;         //图片
    private String rebate;          //折扣
    private String label;           //标签
    private String prescriptionType;//处方类型
    private String basicUnit;       //基本单位
    private String brand;           //品牌
    private String approvalNumber;  //批准文号
    private String placeOfOrigin;   //产地
    private String component;       //成分

    @ExcelField(title = "药名",align=2,sort=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "功能",align=2,sort=2)
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @ExcelField(title = "原价",align=2,sort=3)
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    @ExcelField(title = "折后价",align=2,sort=4)
    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    @ExcelField(title = "图片",align=2,sort=5)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @ExcelField(title = "折扣",align=2,sort=6)
    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    @ExcelField(title = "标签",align=2,sort=7)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @ExcelField(title = "处方类型",align=2,sort=8)
    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    @ExcelField(title = "基本单位",align=2,sort=9)
    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
    }

    @ExcelField(title = "品牌",align=2,sort=10)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @ExcelField(title = "批准文号",align=2,sort=11)
    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    @ExcelField(title = "产地",align=2,sort=12)
    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    @ExcelField(title = "成分",align=2,sort=13)
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
