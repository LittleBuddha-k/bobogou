package com.littlebuddha.bobogou.modules.entity.system;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

import java.util.List;

/**
 * 登录用户实体类
 */
public class Operator extends DataEntity<Operator> {

    private String phone;         //个人联系电话
    private String password;      //密码
    private String header;//头像地址
    private String nickname;//昵称
    private Integer sex;           //性别，0=未知，1=男，2=女
    private Integer member;//会员等级，0=普通会员，1=vip
    private Integer integral;//积分
    private Integer healthBeans;//健康豆
    private Integer collectNumber;//收藏数量
    private String signInTime;//签到时间
    private Integer messageStatus;//消息接收状态，0=关闭，1=打开
    private Integer userAgreement;//是否同意用户协议，0=未同意，1=已同意

    private String salt;          //盐值

    private List<Role> roles;            //角色信息

    private Role role;          //角色外键


    private String rolesId; //工具使用变量，重新修改角色属性时使用

    public Operator() {
    }

    public Operator(String nickname) {
        this.nickname = nickname;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRolesId() {
        return rolesId;
    }

    public void setRolesId(String rolesId) {
        this.rolesId = rolesId;
    }
}
