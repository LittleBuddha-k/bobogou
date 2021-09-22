package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品分类第三方关系表实体类
 */
public class GoodsClassify extends DataEntity<GoodsClassify> {

    private String classifyId = "0";//一级分类ID
    private String goodsId = "0";//商品ID

    private Integer level = 1;

    private String levelOneName;//一级分类名

    public GoodsClassify() {
    }

    public GoodsClassify(String id) {
        super(id);
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelOneName() {
        return levelOneName;
    }

    public void setLevelOneName(String levelOneName) {
        this.levelOneName = levelOneName;
    }
}
