package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

import java.util.List;

/**
 * @author ck
 * @date 2020/7/24 15:14
 */
public class Order extends DataEntity<Order> {
    private Integer userId;//用户id
    private String userName;//用户名称
    private String phone;//用户电话

    private String number;//订单编号
    private Double grossAmount;//总金额，单位：分
    private Double paymentAmount;//商品支付金额，单位：分
    private Double actualAmountPaid;//实际支付金额（商品实际支付金额 + 邮费)，单位：分
    private Double deduction;//抵扣金额，单位：分
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
    private String province;//
    private String city;//
    private String district;//
    private String street;//
    private String detailed;//
    private String distributionMode;//配送方式，1=顺丰，2=京东，3=麦康医药
    private String payMode;//支付方式，0=兑换，1=微信，2=支付宝，3=银行卡
    private String type;//类型，0=购买，1=健康豆兑换，2=积分兑换
    private String status;//状态，0=已取消，1=待付款，2=待发货，3=待收货， 4=已完成，5=申请退款，6=已同意退款，7=退款完成

    private String isInvoice;//是否开票

    private String payTime;//支付时间
    private String refundReason;//退款原因
    private String refundExplain;//退款原因

    private List<OrderInfo> orderInfoList;//子表列表

    //新增详情表显示机构名称
    private String name;

    private String provinceIds;//仅供查询使用
    private String cityIds;//仅供查询使用
    private String areaIds;//仅供查询使用
    private String streetIds;//仅供查询使用

    public Order() {
    }

    public Order(String id) {
        super(id);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
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
        if (getProvince() != null || getCity() != null || getDistrict() != null || getStreet() != null || getDetailed() != null) {
            this.address = getProvince() + getCity() + getDistrict() + getStreet() + getDetailed();
        } else if (getProvince() == null && getCity() == null && getDistrict() == null && getStreet() == null && getDetailed() == null) {
            this.address = "用户未填写地址·";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode;
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

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(String provinceIds) {
        this.provinceIds = provinceIds;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getStreetIds() {
        return streetIds;
    }

    public void setStreetIds(String streetIds) {
        this.streetIds = streetIds;
    }
}
