package com.littlebuddha.bobogou.modules.mapper.system;

import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperatorMapper extends BaseMapper<Operator> {

    Operator getOperatorByName(Operator operator);

    /**
     * 对operator---role表的操作
     *
     * @param operatorRole
     * @return
     */
    //int insertOperatorRole(@Param("operatorId") String operatorId,@Param("roleId") String roleId);
    int insertOperatorRole(OperatorRole operatorRole);

    /**
     * 根据用户更新operator-role中间表数据
     *
     * @param operatorRole
     * @return
     */
    int updateOperatorRole(OperatorRole operatorRole);

    /**
     * 根据用户删除operator-role中间表数据
     *
     * @param operatorRole
     * @return
     */
    int deleteOperatorRole(OperatorRole operatorRole);

    /**
     * 根据用户和角色查询是否有operator-role数据
     *
     * @param util
     * @return
     */
    OperatorRole getOperatorRole(OperatorRole util);

    List<Menu> getMenusByRole(Role role);

    /**
     * 通过角色查找菜单
     *
     * @param role
     * @return
     */
    List<RoleMenu> getRoleMenusByRole(RoleMenu roleMenu);

    void updateUserId(Operator operator);

    List<Operator> findNoPageList(Operator operator);
}
