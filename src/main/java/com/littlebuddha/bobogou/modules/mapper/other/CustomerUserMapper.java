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
     * 查询列表显示数据
     * @param entity
     * @return
     */
    List<CustomerUser> findListData(CustomerUser entity);

    /**
     * 查询已过期会员数据
     * @param entity
     * @return
     */
    List<CustomerUser> findVipOverStayedData(CustomerUser entity);

    /**
     * 当前用户为区级管理时，根据VIP提交的审核数据查询当前用户区域所在的VIP审核提交
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    List<CustomerUser> getVipApplyForAreaManager(CustomerUser entity);

    /**
     * 恢复会员状态
     * @param customerUser
     * @return
     */
    int recoveryVip(CustomerUser customerUser);

    /**
     * 删除订单商品信息时的更新方法
     * @param integral
     * @param healthBeans
     */
    void updateIntegralAndHealthBeans(@Param("integral") int integral,@Param("healthBeans") int healthBeans,@Param("userId") String userId);
}
