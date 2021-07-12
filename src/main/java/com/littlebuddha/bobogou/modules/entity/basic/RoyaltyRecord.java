package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 提成记录实体类
 */
public class RoyaltyRecord extends DataEntity<RoyaltyRecord> {

    private CustomerUser customerUser;
    private Integer userId;//用户ID，关联ud_user表
    private String userName;//用户ID，关联ud_user表
    private Order order;
    private Integer orderId;//订单ID，关联sd_order表
    private String title;//类目
    private Integer money;//提成金额，单位：分
    private Integer atio;//提成比例
    private Integer type;//类型，1=收入，2=支出
    private String orderNumber;//微信提现单号
    private Integer status;//提现是否成功，1=提现中，2=提现成功（已到账）
    private Date successfulTime;//提现成功时间

    public RoyaltyRecord() {
    }

    public RoyaltyRecord(String id) {
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
        this.userName = userName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getAtio() {
        return atio;
    }

    public void setAtio(Integer atio) {
        this.atio = atio;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSuccessfulTime() {
        return successfulTime;
    }

    public void setSuccessfulTime(Date successfulTime) {
        this.successfulTime = successfulTime;
    }
}
