package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author ck
 * @date 2020/7/24 15:14
 */
public class Order extends DataEntity<Order> {
    private String number;//订单编号
    private Double grossAmount;//总金额，单位：分
    private Integer integral;//积分
    private Integer healthBeans;//健康豆
    private Integer addressId;//配送地址ID
    private Integer distributionMode;//配送方式，1=顺丰，2=京东
    private String trackingNo;//物流单号
    private Integer payMode;//支付方式，0=兑换，1=微信，2=支付宝，3=银行卡
    private Integer type;//类型，0=购买，1=健康豆兑换，2=积分兑换
    private Integer status;//状态，0=已取消，1=待付款，2=待收货，3=已完成，4=申请退款，5=已退款
    private String payTime;//支付时间
    private String refundReason;//退款原因

    public Order() {
    }

    public Order(String id) {
        super(id);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(Integer healthBeans) {
        this.healthBeans = healthBeans;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(Integer distributionMode) {
        this.distributionMode = distributionMode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}
