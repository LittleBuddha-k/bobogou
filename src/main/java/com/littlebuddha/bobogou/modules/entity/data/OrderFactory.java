package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

import java.util.List;

/**
 * 订单厂商实体类
 * @author ck
 * @date 2020/7/24 15:14
 */
public class OrderFactory extends DataEntity<OrderFactory> {

    private String orderId;//订单ID，关联sd_order表
    private String orderNumber;//查询的订单编号
    private String factoryId;//厂商ID，关联md_factory表
    private String factoryName;//厂商名字
    private String provinceId;//省ID，关联system_province表
    private String province;//省
    private String cityId;//市ID，关联system_city表
    private String city;//市
    private String areaId;//区ID，关联system_area表
    private String area;//区
    private String streetId;//街道ID，关联system_street表
    private String street;//街道
    private String detailAddress;//详细地址
    private String addressId;//收获地址，关联ud_address表
    private String receivingProvince;
    private String receivingCity;
    private String receivingArea;
    private String receivingStreet;
    private String address;//收获地址，查询的收货地址
    private String receivingAddress;//收获详细地址
    private String totalWeight;//总重量，单位：g
    private String freightWeight;//付费重量，单位：g
    private Double freight;//运费，单位：分
    private String distributionMode;//配送方式，0=未发货，1=顺丰，2=京东，3=其它物流
    private String trackingNo;//物流单号
    private String outStatus;//出库状态，0=已发货，1=未发货
    private String deliveryTime;//确认发货时间

    public OrderFactory() {
    }

    public OrderFactory(String id) {
        super(id);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getReceivingProvince() {
        return receivingProvince;
    }

    public void setReceivingProvince(String receivingProvince) {
        this.receivingProvince = receivingProvince;
    }

    public String getReceivingCity() {
        return receivingCity;
    }

    public void setReceivingCity(String receivingCity) {
        this.receivingCity = receivingCity;
    }

    public String getReceivingArea() {
        return receivingArea;
    }

    public void setReceivingArea(String receivingArea) {
        this.receivingArea = receivingArea;
    }

    public String getReceivingStreet() {
        return receivingStreet;
    }

    public void setReceivingStreet(String receivingStreet) {
        this.receivingStreet = receivingStreet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceivingAddress() {
        if (getReceivingProvince() != null || getReceivingCity() != null || getReceivingArea() != null || getReceivingStreet() != null || getAddress() != null) {
            this.receivingAddress = getReceivingProvince() + getReceivingCity() + getReceivingArea() + getReceivingStreet() + getAddress();
        } else if (getReceivingProvince() != null && getReceivingCity() != null && getReceivingArea() != null && getReceivingStreet() != null && getAddress() != null) {
            this.receivingAddress = "用户未填写地址·";
        }
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getFreightWeight() {
        return freightWeight;
    }

    public void setFreightWeight(String freightWeight) {
        this.freightWeight = freightWeight;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
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

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
