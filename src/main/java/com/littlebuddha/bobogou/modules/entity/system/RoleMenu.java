package com.littlebuddha.bobogou.modules.entity.system;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * role-menu工具实体类
 */
public class RoleMenu extends DataEntity<RoleMenu> {

    private Role role;

    private Menu menu;

    public RoleMenu() {
    }

    public RoleMenu(Role role) {
        this.role = role;
    }

    public RoleMenu(Menu menu) {
        this.menu = menu;
    }

    public RoleMenu(Role role, Menu menu) {
        this.role = role;
        this.menu = menu;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
