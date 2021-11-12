package com.littlebuddha.bobogou.modules.entity.data.utils;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * @author ck
 * @date 2020/7/24 15:14
 * 订单导出工具类
 */
public class OrderExportDTO extends DataEntity<OrderExportDTO> {

    private String orderInfoId;//订单商品id-----用于去重
    private Integer userId;//用户id
    private String userName;//用户名称
    private String phone;//用户电话
    private String number;//订单编号
    private Double grossAmount;//总金额，单位：分
    private String address;//
    private String province;//
    private String city;//
    private String district;//
    private String street;//
    private String detailed;//
    private String distributionMode;//配送方式，1=顺丰，2=京东，3=麦康医药
    private String status;//状态，0=已取消，1=待付款，2=待发货，3=待收货， 4=已完成，5=申请退款，6=已同意退款，7=退款完成
    private String isInvoice;//是否开票
    private String name;//新增详情表显示机构名称
    private Double paymentAmount;//商品支付金额=商品总价，单位：分
    private Double actualAmountPaid;//实际支付金额（商品实际支付金额 + 邮费)，单位：分
    private Double deduction;//抵扣金额，单位：分
    private Integer healthBeans;//健康豆
    private String payMode;//支付方式，0=兑换，1=微信，2=支付宝，3=银行卡
    private String type;//类型，0=购买，1=健康豆兑换，2=积分兑换
    private String payTime;//支付时间

    private String trackingNo;//物流单号

    private Double freight;//运费，单位：分
    private Double managementCost;//本订单收取的管理费，单位：分

    private String goodsName;//商品名称
    private String specification;//商品规格
    private Double price;//商品单价
    private String amount;//购买数量
    private String stockAmount;//库存
    private String authorization;//
    private String factoryName;//
    private String expirationDate;//

    public String getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(String orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @ExcelField(title = "用户", type = 1, sort = 1)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelField(title = "用户电话", type = 1, sort = 2)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ExcelField(title = "订单编号", type = 1, sort = 3)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ExcelField(title = "总金额", type = 1, sort = 4)
    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    @ExcelField(title = "配送地址", type = 1, sort = 5)
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

    @ExcelField(title = "配送方式", type = 1, sort = 6)
    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode;
    }

    @ExcelField(title = "支付方式", type = 1, sort = 7)
    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    @ExcelField(title = "类型", type = 1, sort = 8)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ExcelField(title = "状态", type = 1, sort = 9)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ExcelField(title = "支付时间", type = 1, sort = 10)
    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @ExcelField(title = "是否开票", type = 1, sort = 11)
    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    @ExcelField(title = "机构名称", type = 1, sort = 12)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "商品总价", type = 1, sort = 13)
    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @ExcelField(title = "实际支付金额", type = 1, sort = 14)
    public Double getActualAmountPaid() {
        return actualAmountPaid;
    }

    public void setActualAmountPaid(Double actualAmountPaid) {
        this.actualAmountPaid = actualAmountPaid;
    }

    @ExcelField(title = "抵扣金额", type = 1, sort = 15)
    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    @ExcelField(title = "抵扣播播豆", type = 1, sort = 16)
    public Integer getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(Integer healthBeans) {
        this.healthBeans = healthBeans;
    }

    //@ExcelField(title = "物流单号", type = 1, sort = 17)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @ExcelField(title = "总运费", type = 1, sort = 17)
    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    @ExcelField(title = "总管理费", type = 1, sort = 18)
    public Double getManagementCost() {
        return managementCost;
    }

    public void setManagementCost(Double managementCost) {
        this.managementCost = managementCost;
    }

    @ExcelField(title = "商品名称", type = 1, sort = 19)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @ExcelField(title = "商品规格", type = 1, sort = 20)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @ExcelField(title = "集采价", type = 1, sort = 21)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @ExcelField(title = "购买数量", type = 1, sort = 22)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @ExcelField(title = "商品库存", type = 1, sort = 23)
    public String getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(String stockAmount) {
        this.stockAmount = stockAmount;
    }

    @ExcelField(title = "商品批号", type = 1, sort = 24)
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @ExcelField(title = "供货商", type = 1, sort = 25)
    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    @ExcelField(title = "有效期(月)", type = 1, sort = 26)
    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
