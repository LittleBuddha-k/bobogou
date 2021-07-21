package com.littlebuddha.bobogou.modules.mapper.other;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图标mapper层
 */
@Mapper
public interface CustomerUserMapper extends BaseMapper<CustomerUser> {
    int beVip(CustomerUser customerUser);

    //通过号码查询用户
    CustomerUser getByPhone(CustomerUser customerUser);

    CustomerUser getByOperator(CustomerUser selectOption);

    List<CustomerUser> getToDoList(CustomerUser entity);

    /**
     * 当前用户为区级管理时，根据VIP提交的审核数据查询当前用户区域所在的VIP审核提交
     * @param provinceIds
     * @param cityIds
     * @param areaIds
     * @param streetIds
     * @return
     */
    List<CustomerUser> getVipApplyForAreaManager(@Param("provinceIds")String provinceIds, @Param("cityIds")String cityIds, @Param("areaIds")String areaIds, @Param("streetIds")String streetIds);
}
