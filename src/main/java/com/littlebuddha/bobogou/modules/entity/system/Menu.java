package com.littlebuddha.bobogou.modules.entity.system;


import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体类，一个才对对应具有一种权限
 */
public class Menu extends DataEntity<Menu> {

    private Menu parent;    // 父级菜单
    private String parentId; //父级ID
    private String parentIds; // 所有父级编号
    private String title;    // 名称
    private String href;    // 链接
    private String target;    // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon = "layui-icon layui-icon-layer";    // 图标
    private Integer sort;    // 排序
    private Integer isShow;    // 是否在菜单中显示（1：显示；0：不显示）
    private Integer type; //按钮类型（1：按钮；0：菜单）
    private String permission; // 权限标识
    private boolean hasChildren = false;//（true：有子集；false：无子集,默认值false）

    private List<Menu> child;    // 子级菜单列表

    private Role role;//作为外键使用

    private Operator operator;//作为外键使用

    public Menu() {
        super();
    }

    public Menu(String id) {
        super(id);
    }

    public Menu(Role role) {
        this.role = role;
    }

    public Menu(Operator operator) {
        this.operator = operator;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getParentId() {
        if (parent != null && StringUtils.isNotBlank(parent.getId())) {
            parentId = parent.getId();
        }
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<Menu> getChild() {
        if (child == null) {
            child = new ArrayList<>();
        }
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }
}