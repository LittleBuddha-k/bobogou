package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * 轮播图实体类
 */
public class Banner extends DataEntity<Banner> {

    private String url;   //地址
    private Integer type; //展示位置 1=首页
    private String link;  //链接地址
    private Integer status;//状态，0=隐藏，1=展示

    public Banner() {
    }

    public Banner(String id) {
        super(id);
    }

    @ExcelField(title = "地址", align = 2, sort = 1)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ExcelField(title = "展示位置", align = 2, sort = 2)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @ExcelField(title = "链接地址", align = 2, sort = 3)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @ExcelField(title = "状态", align = 2, sort = 4)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
