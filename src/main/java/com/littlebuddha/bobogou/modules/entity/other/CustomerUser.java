package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-22
 * \* Time: 下午 02:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * App客户实体类
 */
public class CustomerUser extends DataEntity<CustomerUser> {

    private String phone;//手机号
    private String password;//加密后的密码
    private String header;//头像地址
    private String nickname;//昵称
    private Integer sex;//性别，0=未知，1=男，2=女
    private Integer member;//会员等级，0=普通会员，1=vip
    private Integer integral;//积分
    private Integer healthBeans;//健康豆
    //private Integer collectNumber;//收藏数量
    private String signInTime;//签到时间
    private Integer messageStatus;//消息接收状态，0=关闭，1=打开
    private Integer userAgreement;//是否同意用户协议，0=未同意，1=已同意
    private Integer applyStatus;//会员申请状态 0：未审核 1：已通过 2：已拒绝

    public CustomerUser() {
    }

    public CustomerUser(String id) {
        super(id);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
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

    /*public Integer getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Integer collectNumber) {
        this.collectNumber = collectNumber;
    }*/

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Integer getUserAgreement() {
        return userAgreement;
    }

    public void setUserAgreement(Integer userAgreement) {
        this.userAgreement = userAgreement;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }
}
