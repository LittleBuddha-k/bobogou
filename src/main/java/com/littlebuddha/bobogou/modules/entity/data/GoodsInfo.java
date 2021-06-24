package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品详情实体类
 */
public class GoodsInfo extends DataEntity<GoodsInfo> {

    private String content;//商品详情内容
    private String accountId;//更新人

    private Goods medicine;//商品外键
    private String goodsId;//

    public GoodsInfo() {
    }

    public GoodsInfo(String id) {
        super(id);
    }

    public GoodsInfo(Goods medicine) {
        this.medicine = medicine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccountId() {
        if (updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Goods getMedicine() {
        return medicine;
    }

    public void setMedicine(Goods medicine) {
        this.medicine = medicine;
    }

    public String getGoodsId() {
        if (medicine != null && StringUtils.isNotBlank(medicine.getId())){
            this.goodsId = medicine.getId();
        }
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
