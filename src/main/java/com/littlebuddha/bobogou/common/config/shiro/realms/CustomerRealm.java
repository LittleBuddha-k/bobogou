package com.littlebuddha.bobogou.common.config.shiro.realms;

import com.littlebuddha.bobogou.common.utils.ApplicationContextUtils;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.system.OperatorService;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerRealm extends AuthorizingRealm {

    /**
     * 授权---赋予角色信息、权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        OperatorService operatorService = (OperatorService) ApplicationContextUtils.getBean("operatorService");
        RoleService roleService = (RoleService) ApplicationContextUtils.getBean("roleService");
        //根据完整的用户信息查询用户角色以及用户权限
        Operator operator = (Operator) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<OperatorRole> rolesByOperator = operatorService.findRolesByOperator(operator);
        if (!CollectionUtils.isEmpty(rolesByOperator)) {
            for (OperatorRole operatorRole : rolesByOperator) {
                if (operatorRole != null && operatorRole.getRole() != null){
                    Role role = roleService.get(operatorRole.getRole());
                    if (role != null) {
                        simpleAuthorizationInfo.addRole(role.getEnglishName());
                    }
                }
            }
            /*roles.forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getEnglishName());
            });*/
            for (OperatorRole operatorRole : rolesByOperator) {
                List<Menu> menus = new ArrayList<>();
                if (operatorRole != null && operatorRole.getRole() != null){
                    menus = operatorService.findPermissions(operatorRole.getRole());
                }
                menus.forEach(menu -> {
                    simpleAuthorizationInfo.addStringPermission(menu.getPermission());
                });
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        OperatorService operatorService = (OperatorService) ApplicationContextUtils.getBean("operatorService");
        String principal = (String) authenticationToken.getPrincipal();
        //用户注册不能同名
        Operator operator = operatorService.findOperatorByName(new Operator(principal));

        if (!ObjectUtils.isEmpty(operator)) {
            return new SimpleAuthenticationInfo(operator, operator.getPassword(), ByteSource.Util.bytes(operator.getSalt()), this.getName());
        }
        return null;
    }

}
