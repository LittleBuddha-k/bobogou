package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;

/**
 * 商品标签实体类
 */
public class GoodsTag extends DataEntity<GoodsTag> {

    private String icon;//图标地址
    private String name;//标签名称
    private String accountId;//最后操作人ID

    private Operator operator;//外键使用

    public GoodsTag() {
    }

    public GoodsTag(String id) {
        super(id);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ExcelField(title = "分类名称", align = 2, sort = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "分类名称", align = 2, sort = 2)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
