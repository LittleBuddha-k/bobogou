package com.littlebuddha.bobogou.modules.entity.system;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 用户区域实体类
 */
public class OperatorRegion extends DataEntity<OperatorRegion> {

    private Operator operator;//关联后端外键
    private String operatorId;//后台账号ID，关联system_operator表
    private String operatorName;
    private CustomerUser customerUser;//关联前端用户
    private String userId = "0";//前端账号ID，关联ud_user表
    private String userName;
    private Integer type;//类型，1=超级管理员助理，2=省级经纪人，3=市级经济人，4=区级经济人
    private Province province;
    private String provinceId = "0";//省ID，关联system_province表，超级管理助力分配
    private String provinceName;
    private City city;
    private String cityId = "0";//市ID，关联system_city表，超级管理员助力不填，分配给省级经济人管理的市
    private String cityName;
    private Area area;
    private String areaId = "0";//区县ID，关联system_area表，超级管理员助力和省级经济人不填，分配给市级经纪人管理的区县
    private String areaName;
    private Street street;
    private String streetId = "0";//乡镇街道ID，关联system_street，超级管理员助力和省级经纪人以及市级经济人不填，分配给区级经济人管理的乡镇街道
    private String streetName;

    public OperatorRegion() {
    }

    public OperatorRegion(String id) {
        super(id);
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        if (operator != null && StringUtils.isNotBlank(operator.getNickname())){
            operatorName = operator.getNickname();
        }
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
            userName = customerUser.getNickname();
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        if (province != null && StringUtils.isNotBlank(province.getName())){
            provinceName = province.getName();
        }
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        if (city != null && StringUtils.isNotBlank(city.getName())){
            cityName = city.getName();
        }
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        if (area != null && StringUtils.isNotBlank(area.getName())){
            areaName = area.getName();
        }
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getStreetName() {
        if (street != null && StringUtils.isNotBlank(street.getName())){
            streetName = street.getName();
        }
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
