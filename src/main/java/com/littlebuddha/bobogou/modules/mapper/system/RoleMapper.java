package com.littlebuddha.bobogou.modules.mapper.system;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.entity.system.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户名查询角色列表
     * @return
     */
    List<Role> getRolesByOperator(Role role);

    List<Role> findByParentIdsLike(Role update);

    void updateParentIds(Role role);

    /**
     * 角色编辑时为了显示当前角色的上级角色
     * @param entity
     * @return
     */
    List<Role> findNoAddList(Role entity);
}
