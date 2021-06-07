package com.littlebuddha.bobogou.modules.entity.system.utils;

import com.littlebuddha.bobogou.modules.entity.system.Role;

/**
 * 角色添加权限时的传输帮助类
 */
public class RoleMenuTDO {

    private Role role;

    //仅用于对角色赋予权限时
    private String menuIds;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}
