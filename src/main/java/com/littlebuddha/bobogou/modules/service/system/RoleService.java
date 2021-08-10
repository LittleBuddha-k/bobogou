package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.entity.system.RoleMenu;
import com.littlebuddha.bobogou.modules.mapper.system.MenuMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleService extends CrudService<Role, RoleMapper> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public Role get(Role entity) {
        return super.get(entity);
    }

    /**
     * 获取最顶级的一个角色---仅为工具角色
     *
     * @param
     * @return
     */
    public Role getTopRole() {
        Role role = new Role();
        role.setId("-1");
        Role topRole = get(role);
        return topRole;
    }

    @Override
    public List<Role> findList(Role entity) {
        return super.findList(entity);
    }

    @Override
    public List<Role> findAllList(Role entity) {
        return super.findAllList(entity);
    }

    @Override
    public PageInfo<Role> findPage(Page<Role> page, Role entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Role entity) {
        // 获取父节点实体
        Role parent = roleMapper.get(new Role(entity.getParentId()));
        entity.setParent(parent);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        // 设置新的父节点串
        entity.setParentIds(entity.getParent().getParentIds()+entity.getParent().getId()+",");

        int save = super.save(entity);

        // 更新子节点 parentIds
        Role update = new Role();
        update.setParentIds("%,"+entity.getId()+",%");
        List<Role> list = roleMapper.findByParentIdsLike(update);
        for (Role role : list){
            role.setParentIds(role.getParentIds().replace(oldParentIds, entity.getParentIds()));
            roleMapper.updateParentIds(role);
        }
        return save;
    }

    @Override
    @Transactional
    public int deleteByPhysics(Role entity) {
        int row = super.deleteByPhysics(entity);
        roleMenuMapper.deleteByPhysics(new RoleMenu(entity));
        return row;
    }

    @Override
    @Transactional
    public int deleteByLogic(Role entity) {
        //删除角色表的角色
        int i = super.deleteByLogic(entity);
        //删除角色-菜单中的角色-菜单数据
        if (StringUtils.isNotBlank(entity.getId())){
            roleMenuMapper.deleteLogicByRole(new RoleMenu(entity));
        }
        return i;
    }

    @Override
    @Transactional
    public int recovery(Role entity) {
        int recovery = super.recovery(entity);
        return recovery;
    }

    @Transactional
    public int addPermission(Role role) {
        int row = 0;
        //1.判定传参中的menusId
        if (role != null && role.getMenusId() != null && StringUtils.isNotBlank(role.getMenusId())) {
            String[] menusId = role.getMenusId().split(",");
            //直接在每次保存前删除所有角色用户关联信息，然后再根据传参重新赋值
            if (StringUtils.isNotBlank(role.getId())) {
                roleMenuMapper.deleteOutByRole(role.getId());
            }
            //循环得到每个关联关系，做插入处理
            for (String menuId : menusId) {
                Menu menu = menuMapper.get(new Menu(menuId));
                RoleMenu roleMenu = new RoleMenu(role, menu);
                roleMenu.preInsert();
                row = roleMenuMapper.insert(roleMenu);
            }
        } else if (role != null && StringUtils.isBlank(role.getMenusId())) {
            //当角色设定id传参存在，menusId没有值，代表取消所有菜单
            if (StringUtils.isNotBlank(role.getId())) {
                roleMenuMapper.deleteOutByRole(role.getId());
            }
        }
        return row;
    }

    /**
     * 根据角色查询相关菜单做回显
     *
     * @param role
     * @return
     */
    public List<Menu> findMenusByRole(Role role) {
        List<Menu> menus = menuMapper.getMenusByRole(new Menu(role));
        return menus;
    }

    /**
     * 角色编辑时为了显示当前角色的上级角色
     * @param entity
     * @return
     */
    public List<Role> findNoAddList(Role entity) {
        List<Role>  list = roleMapper.findNoAddList(entity);
        return list;
    }

    /**
     * 根据当前用户查询所有不分页角色数据
     * @param role
     * @return
     */
    public List<Role> findAllData(Role role) {
        List<Role> list = roleMapper.findAllData(role);
        return list;
    }
}
