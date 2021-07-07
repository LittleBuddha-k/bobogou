package com.littlebuddha.bobogou.modules.mapper.other;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.Vip;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天mapper层
 */
@Mapper
public interface VipMapper extends BaseMapper<Vip> {

    /**
     * 通过类型查询vip规则数据
     * @param vip
     * @return
     */
    Vip getByType(Vip vip);
}
