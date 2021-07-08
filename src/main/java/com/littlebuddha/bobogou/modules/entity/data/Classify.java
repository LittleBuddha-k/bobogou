package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品分类实体类
 */
public class Classify extends DataEntity<Classify> {

    private String name;//分类名称
    private String icon;//分类图标地址
    private String parentId;//父级分类ID，顶级=0
    private Integer level;//级别分类字段

    private Double provinceRatio;//省代理提成比例，单位：100%，最多三位小数，只有第三级的时候需要填
    private Double cityRatio;//市代理提成比例，单位：100%，最多三位小数，只有第三级的时候需要填
    private Double districtRatio;//区代理提成比例，单位：100%，最多三位小数，只有第三级的时候需要填


    private String accountId;//最后操作人ID

    private Classify goodsType;//商品分类外键
    private String parentName;//分类名称



    public Classify() {
    }

    public Classify(Integer level) {
        this.level = level;
    }

    @ExcelField(title = "分类名称", align = 2, sort = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "分类图标地址", align = 2, sort = 1)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ExcelField(title = "父级分类ID", align = 2, sort = 1)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getProvinceRatio() {
        return provinceRatio;
    }

    public void setProvinceRatio(Double provinceRatio) {
        this.provinceRatio = provinceRatio;
    }

    public Double getCityRatio() {
        return cityRatio;
    }

    public void setCityRatio(Double cityRatio) {
        this.cityRatio = cityRatio;
    }

    public Double getDistrictRatio() {
        return districtRatio;
    }

    public void setDistrictRatio(Double districtRatio) {
        this.districtRatio = districtRatio;
    }

    @ExcelField(title = "最后操作人ID", align = 2, sort = 1)
    public String getAccountId() {
        if(this.updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Classify getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Classify goodsType) {
        this.goodsType = goodsType;
    }

    public String getParentName() {
        if (goodsType != null && StringUtils.isNotBlank(goodsType.getName()))
            this.parentName = goodsType.getName();
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
