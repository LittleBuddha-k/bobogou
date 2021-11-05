package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * 退单实体类
 *
 * @author ck
 * @date 2020/7/24 15:14
 */
public class ChargeBack extends DataEntity<ChargeBack> {

    private String orderId;            //订单id
    private String number;              //订单编号
    private String orderInfoId;        //订单商品Id
    private String userId;             //购买者---app用户id
    private String userName;            //购买人名字
    private String goodsId;            //商品id
    private String goodsName;           //商品名字
    private Integer count;              //退单数量
    private Double price;              //购买的商品单价
    private Double managementExpense;   //商品管理费
    private Double transportationCost;  //运费

    public ChargeBack() {
    }

    public ChargeBack(String id) {
        super(id);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(String orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getManagementExpense() {
        return managementExpense;
    }

    public void setManagementExpense(Double managementExpense) {
        this.managementExpense = managementExpense;
    }

    public Double getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(Double transportationCost) {
        this.transportationCost = transportationCost;
    }
}
