package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 区域--商品实体类
 */
public class RegionGoods extends DataEntity<RegionGoods> {

    private Province province;
    private String provinceId;//省ID
    private String provinceName;

    private City city;
    private String cityId;//市ID
    private String cityName;

    private Area area;
    private String districtId;//区ID
    private String areaName;

    private Street street;//
    private String streetId;//乡镇街道ID
    private String streetName;

    private Goods medicine;
    private String goodsId;//商品ID
    private String goodsName;

    private Integer amount;//商品数量
    private Integer salesVolume;//销量
    private String isMarket;//是否销售，0=在售，1=停售

    private String accountId;//最后操作人ID
    private String updateByName;//

    private List<RegionGoods> regionGoodsList;//商品区域下的商品列表

    public RegionGoods() {
    }

    public RegionGoods(String id) {
        super(id);
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

    @ExcelField(title = "省", align = 2, sort = 1)
    public String getProvinceName() {
        if (province != null && StringUtils.isNotBlank(province.getName())) {
            this.provinceName = province.getName();
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

    @ExcelField(title = "市", align = 2, sort = 2)
    public String getCityName() {
        if (city != null && StringUtils.isNotBlank(city.getName())) {
            this.cityName = city.getName();
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

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    @ExcelField(title = "区", align = 2, sort = 3)
    public String getAreaName() {
        if (area != null && StringUtils.isNotBlank(area.getName())) {
            this.areaName = area.getName();
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

    @ExcelField(title = "街道", align = 2, sort = 4)
    public String getStreetName() {
        if (street != null && StringUtils.isNotBlank(street.getName())) {
            this.streetName = street.getName();
        }
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Goods getMedicine() {
        return medicine;
    }

    public void setMedicine(Goods medicine) {
        this.medicine = medicine;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @ExcelField(title = "商品", align = 2, sort = 5)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @ExcelField(title = "商品数量", align = 2, sort = 6)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @ExcelField(title = "销量", align = 2, sort = 7)
    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    @ExcelField(title = "是否销售", align = 2, sort = 8)
    public String getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(String isMarket) {
        this.isMarket = isMarket;
    }

    public String getAccountId() {
        if (updateBy != null && StringUtils.isNotBlank(updateBy.getNickname())) {
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @ExcelField(title = "修改人", align = 2, sort = 9)
    public String getUpdateByName() {
        if (updateBy != null && StringUtils.isNotBlank(updateBy.getNickname())) {
            this.updateByName = updateBy.getNickname();
        }
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public List<RegionGoods> getRegionGoodsList() {
        return regionGoodsList;
    }

    public void setRegionGoodsList(List<RegionGoods> regionGoodsList) {
        this.regionGoodsList = regionGoodsList;
    }
}
