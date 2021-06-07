package com.littlebuddha.bobogou.modules.mapper.system;

import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<RoleMenu> getRoleMenusByRole(RoleMenu roleMenu);

    List<Menu> findByParentIdsLike(Menu updateChildren);

    void updateParentIds(Menu entity);

    List<Menu> getMenusByRole(Menu menu);
}
