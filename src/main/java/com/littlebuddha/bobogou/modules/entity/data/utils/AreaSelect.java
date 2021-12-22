package com.littlebuddha.bobogou.modules.entity.data.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LittleBuddha-k
 * Date: 2021-12-22
 * 区域选择树形结构实体类
 */
public class AreaSelect {

    private String title;
    private String id;
    private String field;

    private List<AreaSelect> children = new ArrayList<>();

    private String href;
    private Boolean spread;
    private Boolean checked;
    private Boolean disabled;

    private String code;//当前区域的code
    private String parentCode;//上级区域code

    public AreaSelect() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<AreaSelect> getChildren() {
        return children;
    }

    public void setChildren(List<AreaSelect> children) {
        this.children = children;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}