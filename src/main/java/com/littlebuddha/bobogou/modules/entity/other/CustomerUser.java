package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import org.apache.commons.lang3.StringUtils;

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
    private Integer collectNumber;//收藏数量
    private String signInTime;//签到时间
    private Integer purchaseAmount;//成功下单购买次数
    private Integer messageStatus;//消息接收状态，0=关闭，1=打开
    private Integer userAgreement;//是否同意用户协议，0=未同意，1=已同意
    private Integer applyStatus;//会员申请状态 0=未申请，1=已申请，2=已通过，3=已拒绝
    private String vipExpire;//VIP到期时间，后台在通过VIP申请的时候填入申请通过的当前时间
    private Integer areaManager;//区域管理员等级，0=前端用户，1=省管理员，2=市管理员，3=区管理员
    private Double profitAmount;//分润金额（可提现金额），单位：分
    private Double amountWithdrawn;//分润冻结金额（分润不可提现金额），单位：分
    private Double withdrawalAmount;//已提现金额，单位：分
    private Operator operator;//后台管理员ID，区域管理员ID，关联system_operator表
    private String operatorId;//
    private String actType = "other_customer_user_act";//流程类型，用于记录审核历史记录
    private String nextRole;//审核时提交到的下一角色id
    private Integer status;//状态，0=app未注册，1=app已注册

    private Role currentUserRole;//当前用户角色------查询列表数据时使用

    private String actStatus;//关联查询usermember的审核状态

    //会员表 外键
    private UserMember userMember;//

    //区域查询条件
    String provinceIds = "";
    String cityIds = "";
    String areaIds = "";
    String streetIds = "";

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

    public Integer getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Integer collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public Integer getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Integer purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
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

    public String getVipExpire() {
        return vipExpire;
    }

    public void setVipExpire(String vipExpire) {
        this.vipExpire = vipExpire;
    }

    public Integer getAreaManager() {
        return areaManager;
    }

    public void setAreaManager(Integer areaManager) {
        this.areaManager = areaManager;
    }

    public Double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public Double getAmountWithdrawn() {
        return amountWithdrawn;
    }

    public void setAmountWithdrawn(Double amountWithdrawn) {
        this.amountWithdrawn = amountWithdrawn;
    }

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        if (operator != null && StringUtils.isNotBlank(operator.getId())){
            this.operatorId = operator.getId();
        }
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getNextRole() {
        return nextRole;
    }

    public void setNextRole(String nextRole) {
        this.nextRole = nextRole;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public UserMember getUserMember() {
        return userMember;
    }

    public void setUserMember(UserMember userMember) {
        this.userMember = userMember;
    }

    public Role getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(Role currentUserRole) {
        this.currentUserRole = currentUserRole;
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
