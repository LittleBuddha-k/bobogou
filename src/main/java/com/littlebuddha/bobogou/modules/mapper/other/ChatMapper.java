package com.littlebuddha.bobogou.modules.mapper.other;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.Chat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天mapper层
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
}
