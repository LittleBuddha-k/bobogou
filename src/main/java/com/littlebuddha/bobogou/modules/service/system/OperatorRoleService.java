package com.littlebuddha.bobogou.modules.service.system;

import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OperatorRoleService extends CrudService<Role, RoleMapper> {

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

    /**
     * 同时满足用户id和角色id的用户-角色关联信息
     * @param operatorRole
     * @return
     */
    public List<OperatorRole> findByOperatorAndRole(OperatorRole operatorRole){
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(operatorRole);
        return byOperatorAndRole;
    }

    /**
     * 干掉传参以外的数据
     * @param
     */
    public void deleteOutByOperatorRole(String operatorId,String roleIds){
        operatorRoleMapper.deleteOutByOperatorRole(operatorId,roleIds);
    }

    /**
     * 删除用户所有角色绑定
     * @param operatorId
     */

    public void deleteByOperatorLogic(OperatorRole operatorRole){
        operatorRoleMapper.deleteByOperatorLogic(operatorRole);
    }
    /**
     * 删除用户所有角色绑定
     * @param operatorId
     */
    public void deleteByOperatorPhysics(OperatorRole operatorRole){
        operatorRoleMapper.deleteByOperatorPhysics(operatorRole);
    }
}
