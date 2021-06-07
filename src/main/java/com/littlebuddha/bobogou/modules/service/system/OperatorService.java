package com.littlebuddha.bobogou.modules.service.system;

import com.littlebuddha.bobogou.common.utils.AutoId;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.*;
import com.littlebuddha.bobogou.modules.mapper.system.MenuMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OperatorService extends CrudService<Operator, OperatorMapper> {

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

    @Override
    @Transactional
    public int save(Operator operator) {
        int operatorRow;
        //这一步保存的是operator数据
        if (operator.getIsNewData()) {
            operator.preInsert();
            //获取盐值
            String splicing = AutoId.getSplicing(16);
            Md5Hash md5Hash = new Md5Hash(operator.getPassword(), splicing, 1024);
            operator.setSalt(splicing);
            operator.setPassword(md5Hash.toHex());
            operatorRow = operatorMapper.insert(operator);

            //这里应该将operator form填写的roles数据单独保存到operator-role表格
            List<Role> roles = operator.getRoles();
            OperatorRole util = null;
            if (roles != null && !roles.isEmpty()) {
                for (Role role : roles) {
                    util = new OperatorRole(operator, role);
                    util.preInsert();
                    int i = operatorRoleMapper.insert(util);
                }
            }
        } else {
            operator.preUpdate();
            operatorRow = operatorMapper.update(operator);

            //这里应该将operator form填写的roles数据单独保存到operator-role表格
            List<Role> roles = operator.getRoles();
            OperatorRole util = null;
            if (roles != null && !roles.isEmpty()) {
                for (Role role : roles) {
                    if (StringUtils.isNotBlank(role.getId())) {
                        util = new OperatorRole(operator, role);
                        OperatorRole operatorRole = operatorMapper.getOperatorRole(util);
                        if (operatorRole == null) {
                            util.preInsert();
                            int i = operatorRoleMapper.insert(util);
                        } else {
                            util.preUpdate();
                            int i = operatorRoleMapper.update(util);
                        }
                    }
                }
            }
        }
        return operatorRow;
    }

    @Transactional
    public int register(Operator operator) {
        int save;
        Operator operatorByName = findOperatorByName(operator);
        if (operatorByName != null) {
            save = 0;
            return save;
        } else {
            //获取盐值
            String splicing = AutoId.getSplicing(16);
            Md5Hash md5Hash = new Md5Hash(operator.getPassword(), splicing, 1024);
            operator.setSalt(splicing);
            operator.setPassword(md5Hash.toHex());
            save = super.save(operator);
            return save;
        }
    }

    public Operator findOperatorByName(Operator operator) {
        return operatorMapper.getOperatorByName(operator);
    }

    public List<Role> findRolesByOperator(Operator operator) {
        List<Role> rolesByOperator = roleMapper.getRolesByOperator(new Role(operator));
        return rolesByOperator;
    }

    public List<Menu> findMenusByRole(Role role) {
        List<Menu> menus = operatorMapper.getMenusByRole(role);
        return menus;
    }

    @Override
    @Transactional
    public int deleteByPhysics(Operator entity) {
        int i = super.deleteByPhysics(entity);
        int row = operatorRoleMapper.deleteByPhysics(new OperatorRole(entity));
        return i;
    }

    @Override
    @Transactional
    public int deleteByLogic(Operator entity) {
        int i = super.deleteByLogic(entity);
        int row = operatorRoleMapper.deleteByLogic(new OperatorRole(entity));
        return i;
    }

    @Override
    @Transactional
    public int recovery(Operator entity) {
        int recovery = super.recovery(entity);
        return recovery;
    }

    public List<RoleMenu> findRoleMenusByRole(Role role) {
        List<RoleMenu> roleMenus = operatorMapper.getRoleMenusByRole(new RoleMenu(role));
        for (RoleMenu roleMenu : roleMenus) {
            if (roleMenu.getRole() != null && StringUtils.isNotBlank(roleMenu.getRole().getId())) {

            }
            if (roleMenu.getMenu() != null && StringUtils.isNotBlank(roleMenu.getMenu().getId())) {
                Menu menu = menuMapper.get(roleMenu.getMenu());
                roleMenu.setMenu(menu);
            }
        }
        return roleMenus;
    }


    public List<Menu> getMenusByOperator() {
        Operator currentUser = UserUtils.getCurrentUser();
        List<Role> roles = findRolesByOperator(currentUser);
        List<Menu> menuData = new ArrayList<>();
        List<RoleMenu> roleMenusByRole = new ArrayList<>();
        //1.查询当前用户的所有角色菜单信息
        for (Role role : roles) {
            List<RoleMenu> roleMenus = menuMapper.getRoleMenusByRole(new RoleMenu(role));
            if (roleMenus != null && roleMenus.size() > 0) {
                roleMenusByRole.addAll(roleMenus);
            }
        }
        //2.将所有1得到的menu放入menuList
        for (RoleMenu roleMenu : roleMenusByRole) {
            if (roleMenu != null && StringUtils.isNotBlank(roleMenu.getMenu().getId())) {
                Menu menu = menuMapper.get(roleMenu.getMenu());
                menuData.add(menu);
            }
        }
        return menuData;
    }

    /**
     * 仅用于在用户已存在，给用户设置角色的情形下
     *
     * @param operator
     * @return
     */
    public int addRole(Operator operator) {
        int row = 0;
        if (operator != null && operator.getRolesId() != null && StringUtils.isNotBlank(operator.getRolesId())) {
            String[] rolesId = operator.getRolesId().split(",");
            //直接在每次保存前删除所有角色用户关联信息，然后再根据传参重新赋值
            if (StringUtils.isNotBlank(operator.getId())) {
                operatorRoleMapper.deleteOutByOperator(operator.getId());
            }
            for (String roleId : rolesId) {
                Role role = roleMapper.get(new Role(roleId));
                OperatorRole operatorRole = new OperatorRole(operator, role);
                operatorRole.preInsert();
                row = operatorRoleMapper.insert(operatorRole);
            }
        } else if (operator != null && StringUtils.isBlank(operator.getRolesId())) {
            //当取消所有角色时执行
            if (StringUtils.isNotBlank(operator.getId())) {
                operatorRoleMapper.deleteOutByOperator(operator.getId());
            }
        }
        return row;
    }
}
