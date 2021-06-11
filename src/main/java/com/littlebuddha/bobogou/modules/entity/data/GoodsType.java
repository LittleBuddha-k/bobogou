package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品分类实体类
 */
public class GoodsType extends DataEntity<GoodsType> {

    private String name;//分类名称
    private String icon;//分类图标地址
    private Integer parentId;//父级分类ID，顶级=0
    private String accountId;//最后操作人ID

    private GoodsType goodsType;//商品分类外键
    private String parentName;//分类名称

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
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
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
