package com.littlebuddha.bobogou.common.utils;

import com.littlebuddha.bobogou.modules.entity.other.CustomerService;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
import com.littlebuddha.bobogou.modules.service.system.OperatorRoleService;
import com.littlebuddha.bobogou.modules.service.system.OperatorService;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 与用户相关操作的帮助类
 */
public class UserUtils {

    private static OperatorService operatorService = (OperatorService) ApplicationContextUtils.getBean("operatorService");

    private static OperatorRoleService operatorRoleService = (OperatorRoleService) ApplicationContextUtils.getBean("operatorRoleService");

    private static RoleService roleService = (RoleService) ApplicationContextUtils.getBean("roleService");

    private static CustomerUserService customerUserService = (CustomerUserService) ApplicationContextUtils.getBean("customerUserService");

    public static Operator getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        Operator currentUser = (Operator) subject.getPrincipal();
        return currentUser;
    }

    public static CustomerUser getCurrentCustomerUser() {
        CustomerUser byPhone = null;
        Subject subject = SecurityUtils.getSubject();
        Operator currentUser = (Operator) subject.getPrincipal();
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getPhone())){
            CustomerUser select = new CustomerUser();
            select.setPhone(currentUser.getPhone());
            byPhone = customerUserService.getByPhone(select);
        }
        return byPhone;
    }

    /**
     * 获取当前登录者对象
     */
    public static Operator getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Operator principal = (Operator) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 前端没有做只能设置一个角色的限制，这里暂且只取值第一个角色
     *
     * @return
     */
    public static Role getCurrentUserRole() {
        Operator currentUser = getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleService.findByOperatorAndRole(new OperatorRole(currentUser));
        /**
         * 当前暂且设置一个用户只有一个角色
         */
        OperatorRole operatorRole = byOperatorAndRole.get(0);
        Role currentUserRole = new Role();
        if (operatorRole != null && operatorRole.getRole() != null && StringUtils.isNotBlank(operatorRole.getRole().getId())) {
            currentUserRole = roleService.get(new Role(operatorRole.getRole().getId()));
            return currentUserRole;
        }
        return currentUserRole;
    }

    /**
     * 是否为超级管理员
     *
     * @return
     */
    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }
}
