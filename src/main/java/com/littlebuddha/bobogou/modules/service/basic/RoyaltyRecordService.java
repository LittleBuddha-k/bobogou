package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.RoyaltyRecord;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.basic.RoyaltyRecordMapper;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoyaltyRecordService extends CrudService<RoyaltyRecord, RoyaltyRecordMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Resource
    private RoyaltyRecordMapper royaltyRecordMapper;

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

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
        //??????????????????
        List<RoyaltyRecord> result = new ArrayList<>();
        //????????????????????????
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //?????????????????????????????????list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //??????????????????
        PageInfo<RoyaltyRecord> pageInfo = null;
        //??????????????????????????????????????????????????????
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            if (currentUser != null && "1".equals(currentUser.getId())){//?????????????????????????????????
                result = royaltyRecordMapper.findList(entity);
            }else {
                for (OperatorRegion region : operatorRegionList) {//???????????????????????????
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<RoyaltyRecord> royaltyRecordList = royaltyRecordMapper.findList(entity);
                        if (royaltyRecordList != null) {
                            result.addAll(royaltyRecordList);
                        }
                    }
                }
            }
            //??????
            result = ListUtils.removeDuplicateRoyaltyRecord(result);
            //????????????
            List list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            //????????????
            if (result != null && !result.isEmpty()) {
                pageInfo = new PageInfo<>();
                pageInfo.setList(list);
                pageInfo.setTotal(result.size());
            } else {
                pageInfo = new PageInfo<>();
            }
        }
        return pageInfo;
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
