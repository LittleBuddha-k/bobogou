package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.AutoId;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
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

    @Autowired
    private CustomerUserMapper customerUserMapper;

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
            //如果前端传回的密码与原本数据库密码不匹配，执行修改密码
            Operator initOperator = operatorMapper.get(operator);
            if(StringUtils.isNotBlank(operator.getPassword()) && initOperator != null && !operator.getPassword().equals(initOperator.getPassword())){
                Md5Hash md5Hash = new Md5Hash(operator.getPassword(), initOperator.getSalt(), 1024);
                operator.setPassword(md5Hash.toHex());
            }
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
        //关联保存app用户
        if(operator.getCustomerUser() != null){
            CustomerUser customerUser = operator.getCustomerUser();
            if (CustomerUser.DEL_FLAG_NORMAL.equals(customerUser.getDelFlag())){
                if (!StringUtils.isNotBlank(customerUser.getId())) {
                    customerUser.setIdType("AUTO");
                    customerUser.setPhone(operator.getPhone());
                    customerUser.setOperatorId(operator.getId());
                    customerUser.preInsert();
                    customerUserMapper.insert(customerUser);
                }else {
                    customerUser.setOperatorId(operator.getId());
                    customerUser.preUpdate();
                    customerUserMapper.update(customerUser);
                }
            }else {

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

    public List<OperatorRole> findRolesByOperator(Operator operator) {
        List<OperatorRole> operatorRoleList = operatorRoleMapper.findList(new OperatorRole(operator));
        return operatorRoleList;
    }

    @Override
    public Operator get(Operator entity) {
        return super.get(entity);
    }

    @Override
    public List<Operator> findList(Operator entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Operator> findPage(Page<Operator> page, Operator entity) {
        if (entity != null){
            String loginName = StringUtils.deleteWhitespace(entity.getLoginName());
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setLoginName(loginName);
            entity.setNickname(nickname);
            entity.setPhone(phone);
        }
        return super.findPage(page, entity);
    }

    @Override
    @Transactional
    public int deleteByPhysics(Operator entity) {
        int i = super.deleteByPhysics(entity);
        if(entity != null && StringUtils.isNotBlank(entity.getId())){
            operatorRoleMapper.deleteByOperatorPhysics(new OperatorRole(entity));
        }
        return i;
    }

    @Override
    @Transactional
    public int deleteByLogic(Operator entity) {
        int i = super.deleteByLogic(entity);
        if(entity != null && StringUtils.isNotBlank(entity.getId())){
            operatorRoleMapper.deleteByOperatorLogic(new OperatorRole(entity));
        }
        return i;
    }

    @Override
    @Transactional
    public int recovery(Operator entity) {
        int recovery = super.recovery(entity);
        return recovery;
    }

    /**
     * 查询当前用户对应的菜单权限
     *
     * @param role
     * @return
     */
    public List<Menu> findPermissions(Role role) {
        List<Menu> menus = menuMapper.findList(new Menu(role));
        return menus;
    }

    public List<Menu> getMenusByOperator() {
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> rolesByOperator = findRolesByOperator(currentUser);
        List<Menu> menuData = new ArrayList<>();
        List<Menu> menusByRole = new ArrayList<>();
        //1.查询当前用户的所有角色菜单信息
        for (OperatorRole operatorRole : rolesByOperator) {
            if (operatorRole != null && operatorRole.getRole() !=  null &&StringUtils.isNotBlank(operatorRole.getRole().getId())) {
                List<Menu> menus = menuMapper.findList(new Menu(operatorRole.getRole()));
                if (menus != null && menus.size() > 0) {
                    menusByRole.addAll(menus);
                }
            }
        }
        //2.将所有1得到的menu放入menuList
        for (Menu menu : menusByRole) {
            if (menu != null && StringUtils.isNotBlank(menu.getId())) {
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
                operatorRoleMapper.deleteByOperatorPhysics(new OperatorRole(operator));
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
                operatorRoleMapper.deleteByOperatorPhysics(new OperatorRole(operator));
            }
        }
        return row;
    }
}
