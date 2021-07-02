package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.other.UserMemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserMemberService extends CrudService<UserMember, UserMemberMapper> {

    @Autowired
    private UserMemberMapper userMemberMapper;

    @Override
    public UserMember get(UserMember entity) {
        return super.get(entity);
    }

    @Override
    public List<UserMember> findList(UserMember entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<UserMember> findPage(Page<UserMember> page, UserMember entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(UserMember entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByPhysics(UserMember entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public int deleteByLogic(UserMember entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int recovery(UserMember entity) {
        return super.recovery(entity);
    }

    /**
     * 使用用户查找用户会员数据
     * @param userMember
     * @return
     */
    public UserMember getByUser(UserMember userMember) {
        UserMember entity = userMemberMapper.getByUser(userMember);
        return entity;
    }
}
