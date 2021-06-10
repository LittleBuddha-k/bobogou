package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品品牌规格实体类
 */
public class CommodityBrand extends DataEntity<CommodityBrand> {


    private String brandName;//品牌名称
    private String classifyName;//品牌规格分类名称
    private String parentId;//品牌ID，为品牌时=0
    private String accountId;//最后操作账号ID

    public CommodityBrand() {
    }

    @ExcelField(title = "品牌名称", align = 2, sort = 1)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @ExcelField(title = "品牌规格分类名称", align = 2, sort = 2)
    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAccountId() {
        if(updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
