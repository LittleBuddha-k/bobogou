package com.littlebuddha.bobogou.modules.mapper.other;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图标mapper层
 */
@Mapper
public interface CustomerUserMapper extends BaseMapper<CustomerUser> {
    int beVip(CustomerUser customerUser);
}
