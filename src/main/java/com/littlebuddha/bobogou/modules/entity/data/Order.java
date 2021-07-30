package com.littlebuddha.bobogou.modules.entity.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author ck
 * @date 2020/7/24 15:14
 */
public class Order extends DataEntity<Order> {
    private CustomerUser customerUser;//用户外键
    private Integer userId;//用户id
    private String userName;//用户名称

    private String number;//订单编号
    private Double grossAmount;//总金额，单位：分
    private Double paymentAmount;//商品支付金额，单位：分
    private Double actualAmountPaid;//实际支付金额（商品实际支付金额 + 邮费)，单位：分
    private Double deductiona;//抵扣金额，单位：分
    private Integer integral;//积分
    private Integer healthBeans;//健康豆
    private Integer isFreight;//是否需要运费，0=不需要运费，1=要运费是否需要运费
    private Double totalWeight;//订单总重量，单位：g
    private Double freightWeight;//付费重量，单位：g
    private Double freight;//运费，单位：分
    private Double managementCost;//本订单收取的管理费，单位：分
    private Double provinceCost;//省经理人提成费用，单位：分
    private Double cityCost;//省经理人提成费用，单位：分
    private Double districtCost;//省经理人提成费用，单位：分
    private Double harvestIntegral;//订单完成可获取的积分，订单确认收货和自动确认收获后发放给用户
    private Double harvestHealthBeans;//订单完成可获取的播播豆，订单确认收货和自动确认收获后发放给用户
    private Integer addressId;//配送地址ID
    private String address;//
    private String distributionMode;//配送方式，1=顺丰，2=京东
    private String trackingNo;//物流单号
    private String payMode;//支付方式，0=兑换，1=微信，2=支付宝，3=银行卡
    private String type;//类型，0=购买，1=健康豆兑换，2=积分兑换
    private String status;//状态，0=已取消，1=待付款，2=待发货，3=待收货， 4=已完成，5=申请退款，6=已同意退款，7=退款完成

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String outTime;//出库时间
    private String outStatus;//出库状态
    private String payTime;//支付时间
    private String refundReason;//退款原因
    private String refundExplain;//退款原因

    private List<OrderInfo> orderInfoList;//子表列表

    public Order() {
    }

    public Order(String id) {
        super(id);
    }

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        if (customerUser != null && StringUtils.isNotBlank(customerUser.getNickname())){
            this.userName = customerUser.getNickname();
        }
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
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

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getActualAmountPaid() {
        return actualAmountPaid;
    }

    public void setActualAmountPaid(Double actualAmountPaid) {
        this.actualAmountPaid = actualAmountPaid;
    }

    public Double getDeductiona() {
        return deductiona;
    }

    public void setDeductiona(Double deductiona) {
        this.deductiona = deductiona;
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

    public Integer getIsFreight() {
        return isFreight;
    }

    public void setIsFreight(Integer isFreight) {
        this.isFreight = isFreight;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getFreightWeight() {
        return freightWeight;
    }

    public void setFreightWeight(Double freightWeight) {
        this.freightWeight = freightWeight;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getManagementCost() {
        return managementCost;
    }

    public void setManagementCost(Double managementCost) {
        this.managementCost = managementCost;
    }

    public Double getProvinceCost() {
        return provinceCost;
    }

    public void setProvinceCost(Double provinceCost) {
        this.provinceCost = provinceCost;
    }

    public Double getCityCost() {
        return cityCost;
    }

    public void setCityCost(Double cityCost) {
        this.cityCost = cityCost;
    }

    public Double getDistrictCost() {
        return districtCost;
    }

    public void setDistrictCost(Double districtCost) {
        this.districtCost = districtCost;
    }

    public Double getHarvestIntegral() {
        return harvestIntegral;
    }

    public void setHarvestIntegral(Double harvestIntegral) {
        this.harvestIntegral = harvestIntegral;
    }

    public Double getHarvestHealthBeans() {
        return harvestHealthBeans;
    }

    public void setHarvestHealthBeans(Double harvestHealthBeans) {
        this.harvestHealthBeans = harvestHealthBeans;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
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

    public String getRefundExplain() {
        return refundExplain;
    }

    public void setRefundExplain(String refundExplain) {
        this.refundExplain = refundExplain;
    }

    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
