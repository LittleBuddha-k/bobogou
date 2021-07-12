package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.RoyaltyRecord;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.mapper.basic.RoyaltyRecordMapper;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoyaltyRecordService extends CrudService<RoyaltyRecord, RoyaltyRecordMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private RoyaltyRecordMapper royaltyRecordMapper;

    @Autowired
    private CustomerUserMapper customerUserMapper;

    @Override
    public RoyaltyRecord get(RoyaltyRecord entity) {
        RoyaltyRecord royaltyRecord = royaltyRecordMapper.get(entity);
        return royaltyRecord;
    }

    @Override
    public List<RoyaltyRecord> findList(RoyaltyRecord entity) {
        List<RoyaltyRecord> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<RoyaltyRecord> findPage(Page<RoyaltyRecord> page, RoyaltyRecord entity) {
        PageInfo<RoyaltyRecord> page1 = super.findPage(page, entity);
        List<RoyaltyRecord> list = page1.getList();
        for (RoyaltyRecord royaltyRecord : list) {
            if (royaltyRecord != null && royaltyRecord.getUserId() != null && StringUtils.isNotBlank(royaltyRecord.getUserId().toString())){
                CustomerUser customerUser = customerUserMapper.get(new CustomerUser(royaltyRecord.getUserId().toString()));
                royaltyRecord.setCustomerUser(customerUser);
            }
        }
        return page1;
    }

    @Override
    public int save(RoyaltyRecord entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(RoyaltyRecord entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(RoyaltyRecord entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<RoyaltyRecord> findRecoveryPage(Page<RoyaltyRecord> page, RoyaltyRecord entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(RoyaltyRecord entity) {
        return super.recovery(entity);
    }
}
