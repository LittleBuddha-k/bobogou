package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * @author ck
 * @date 2020/7/24 15:14
 */
public class OrderInfo extends DataEntity<OrderInfo> {

   private String orderId;//订单ID
   private String goodsId;//商品ID，兑换商品为cd_change_goods表ID
   private String goodsImage;//商品封面图片地址
   private String goodsName;//商品名称
   private String specification;//商品规格
   private String amount;//购买数量
   private String price;//商品单价
   private String totalPrice;//总价
   private String type;//类型，0=购买商品，1=兑换商品

    public OrderInfo() {
    }

    public OrderInfo(String id) {
        super(id);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
