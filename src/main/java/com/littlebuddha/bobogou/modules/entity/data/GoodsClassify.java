package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品分类第三方关系表实体类
 */
public class GoodsClassify extends DataEntity<GoodsClassify> {

    private String classifyId;//一级分类ID
    private String secondClassifyId;//二级分类ID
    private String reclassifyId;//三级分类ID
    private String goodsId;//商品ID

    private String levelOneName;//一级分类名
    private String levelTwoName;//一级分类名
    private String levelThreeName;//一级分类名

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

    public String getSecondClassifyId() {
        return secondClassifyId;
    }

    public void setSecondClassifyId(String secondClassifyId) {
        this.secondClassifyId = secondClassifyId;
    }

    public String getReclassifyId() {
        return reclassifyId;
    }

    public void setReclassifyId(String reclassifyId) {
        this.reclassifyId = reclassifyId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getLevelOneName() {
        return levelOneName;
    }

    public void setLevelOneName(String levelOneName) {
        this.levelOneName = levelOneName;
    }

    public String getLevelTwoName() {
        return levelTwoName;
    }

    public void setLevelTwoName(String levelTwoName) {
        this.levelTwoName = levelTwoName;
    }

    public String getLevelThreeName() {
        return levelThreeName;
    }

    public void setLevelThreeName(String levelThreeName) {
        this.levelThreeName = levelThreeName;
    }
}
