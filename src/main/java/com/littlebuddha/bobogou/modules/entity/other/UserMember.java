package com.littlebuddha.bobogou.modules.entity.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-22
 * \* Time: 下午 02:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * App客户会员实体类
 */
public class UserMember extends DataEntity<UserMember> {

    private Integer userId;//用户ID
    private Integer step;//
    private String realName;//真实姓名
    private String phone;//联系电话
    private String cardNo;//身份证号
    private String name;//医院诊所药房名称
    private String shopAddress;//
    private String type;//类型，1=药房，2=诊所，3=医院
    private Integer provinceId;//省ID
    private String province;//省
    private Integer cityId;//市ID
    private String city;//市
    private Integer districtId;//区ID
    private String district;//区
    private Integer streetId;//街道/乡镇ID
    private String street;//街道/乡镇
    private String realAddress;//真实地址
    private String businessLicense;//营业执照地址

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String beginTime;//营业执照有效开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;//营业执照有效结束时间

    private String businessCertificate;//经营许可证地址，多张使用英文逗号分隔，药房为药品经营许可证（包括副本），医疗机构为医疗机构执业许可证（包括变更页）
    private String foodBusinessLicense;//食品经营许可证，药房要经营食品时上传（选填）
    private String isEntrust;//是否委托，0=否，1=是
    private String authorityPurchase;//采购委托书地址（委托必填），多张使用英文逗号分隔
    private String mandatary;//被委托人省份证地址（委托必填），正面在前，背面在后
    private String groupPhoto;//经理人和申请人的合照图片地址（药房或者医疗机构的门店名），一张

    private String contractUrl;//合同文件地址
    private String provinceUserId;//省审核人ID
    private String cityUserId;//市审核人ID
    private String districtUserId;//区审核人ID

    private String status;//审核状态，0=未审核，1=区代理已审核通过，2=区代理审核已经拒绝，3=市代理已审核通过，4=市代理审核已拒绝，5=省代理已审核通过，6=省代理已经拒绝，7=超级管理员助理已审核通过，8=超级管理员助理已经拒绝，9=超级管理员已审核通过，10=超级管理员已经拒绝

    private String provinceRefuseReason;//省级拒绝原因
    private String provincePassReason;//省级通过原因
    private String cityRefuseReason;//市级拒绝原因
    private String cityPassReason;//市级通过原因
    private String districtRefuseReason;//区级拒绝原因
    private String districtPassReason;//区级通过原因


    private Integer vipStatus;//vip已过期，0=正常，1=已过期
    private Integer accountId;//最后操作人ID

    public UserMember() {
    }

    public UserMember(String id) {
        super(id);
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBusinessCertificate() {
        return businessCertificate;
    }

    public void setBusinessCertificate(String businessCertificate) {
        this.businessCertificate = businessCertificate;
    }

    public String getFoodBusinessLicense() {
        return foodBusinessLicense;
    }

    public void setFoodBusinessLicense(String foodBusinessLicense) {
        this.foodBusinessLicense = foodBusinessLicense;
    }

    public String getIsEntrust() {
        return isEntrust;
    }

    public void setIsEntrust(String isEntrust) {
        this.isEntrust = isEntrust;
    }

    public String getAuthorityPurchase() {
        return authorityPurchase;
    }

    public void setAuthorityPurchase(String authorityPurchase) {
        this.authorityPurchase = authorityPurchase;
    }

    public String getMandatary() {
        return mandatary;
    }

    public void setMandatary(String mandatary) {
        this.mandatary = mandatary;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getProvinceUserId() {
        return provinceUserId;
    }

    public void setProvinceUserId(String provinceUserId) {
        this.provinceUserId = provinceUserId;
    }

    public String getCityUserId() {
        return cityUserId;
    }

    public void setCityUserId(String cityUserId) {
        this.cityUserId = cityUserId;
    }

    public String getDistrictUserId() {
        return districtUserId;
    }

    public void setDistrictUserId(String districtUserId) {
        this.districtUserId = districtUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvinceRefuseReason() {
        return provinceRefuseReason;
    }

    public void setProvinceRefuseReason(String provinceRefuseReason) {
        this.provinceRefuseReason = provinceRefuseReason;
    }

    public String getProvincePassReason() {
        return provincePassReason;
    }

    public void setProvincePassReason(String provincePassReason) {
        this.provincePassReason = provincePassReason;
    }

    public String getCityRefuseReason() {
        return cityRefuseReason;
    }

    public void setCityRefuseReason(String cityRefuseReason) {
        this.cityRefuseReason = cityRefuseReason;
    }

    public String getCityPassReason() {
        return cityPassReason;
    }

    public void setCityPassReason(String cityPassReason) {
        this.cityPassReason = cityPassReason;
    }

    public String getDistrictRefuseReason() {
        return districtRefuseReason;
    }

    public void setDistrictRefuseReason(String districtRefuseReason) {
        this.districtRefuseReason = districtRefuseReason;
    }

    public String getDistrictPassReason() {
        return districtPassReason;
    }

    public void setDistrictPassReason(String districtPassReason) {
        this.districtPassReason = districtPassReason;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public Integer getAccountId() {
        if (updateBy !=  null && StringUtils.isNotBlank(updateBy.getId())){
            accountId = Integer.valueOf(updateBy.getId());
        }
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
