package com.littlebuddha.bobogou.modules.mapper.other;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图标mapper层
 */
@Mapper
public interface UserMemberMapper extends BaseMapper<UserMember> {

    /**
     * 通过用户userId查询申请信息
     * @param userMember
     * @return
     */
    UserMember getByUser(UserMember userMember);
}
