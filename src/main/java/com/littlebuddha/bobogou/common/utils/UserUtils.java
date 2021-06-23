package com.littlebuddha.bobogou.common.utils;

import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.service.system.OperatorService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 与用户相关操作的帮助类
 */
public class UserUtils {

    private static OperatorService operatorService = (OperatorService)ApplicationContextUtils.getBean("operatorService");

    public static Operator getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        Operator currentUser = (Operator) subject.getPrincipal();
        return currentUser;
    }

    /**
     * 获取当前登录者对象
     */
    public static Operator getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Operator principal = (Operator)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
//			subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    /**
     * 是否为超级管理员
     * @return
     */
    public static boolean isAdmin(String id){
        return id != null && "1".equals(id);
    }
}
