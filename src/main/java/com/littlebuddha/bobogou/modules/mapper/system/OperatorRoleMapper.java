package com.littlebuddha.bobogou.modules.mapper.system;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface OperatorRoleMapper extends BaseMapper<OperatorRole> {

    /**
     * 同时满足用户id和角色id的用户-角色关联信息
     * @param operatorRole
     * @return
     */
    OperatorRole getByOperatorAndRole(OperatorRole operatorRole);

    /**
     * 干掉传参以外的数据
     * @param
     */
    void deleteOutByOperatorRole(@Param(value = "operatorId") String operatorId, @Param(value = "roleIds") String roleIds);

    /**
     * 删除用户所有角色绑定
     * @param operatorId
     */
    void deleteOutByOperator(String operatorId);
}
