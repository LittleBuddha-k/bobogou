package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ck
 * @date 2020/7/24 15:14
 */
public class OrderInfo extends DataEntity<OrderInfo> {

    private CustomerUser customerUser;//用户外键
    private String userId;//用户ID
    private String userName;//

    private Order order;//外键
    private String orderId;//订单ID

    private String orderFactoryId;//订单厂商表

    private Goods goods;//商品外键
    private String goodsId;//商品ID，兑换商品为cd_change_goods表ID
    private String goodsImage;//商品封面图片地址
    private String goodsName;//商品名称

    private GoodsSpecification goodsSpecification;//商品规格外键
    private String specification;//商品规格

    private Integer amount;//购买数量
    private String stockAmount;//库存
    private String authorization;//
    private String factoryName;//
    private String expirationDate;//
    private String price;//商品单价
    private String totalPrice;//总价
    private Double integral;//单个商品的支付积分
    private Double totalIntegral;//支付总积分
    private Double weight;//单个商品重量，单位：g
    private Double totalWeight;//总重量：单位：g
    private Integer isFreight;//是否需要运费，0=不需要运费，1=要运费是否需要运费
    private Double managementCost;//管理费，单位：分
    private Double harvestIntegral;//购买收货后可获得的积分
    private Double harvestHealthBeans;//购买收货后可获得的播播豆
    private Integer type;//类型，0=购买商品，1=兑换商品
    private Integer status;//状态，0=未评价，1=已评价

    public OrderInfo() {
    }

    public OrderInfo(String id) {
        super(id);
    }

    public OrderInfo(Order order) {
        this.order = order;
    }

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        if (customerUser != null && StringUtils.isNotBlank(customerUser.getNickname())){
            this.userName = customerUser.getNickname();
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderFactoryId() {
        return orderFactoryId;
    }

    public void setOrderFactoryId(String orderFactoryId) {
        this.orderFactoryId = orderFactoryId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public GoodsSpecification getGoodsSpecification() {
        return goodsSpecification;
    }

    public void setGoodsSpecification(GoodsSpecification goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(String stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public Double getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(Double totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getIsFreight() {
        return isFreight;
    }

    public void setIsFreight(Integer isFreight) {
        this.isFreight = isFreight;
    }

    public Double getManagementCost() {
        return managementCost;
    }

    public void setManagementCost(Double managementCost) {
        this.managementCost = managementCost;
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
}
