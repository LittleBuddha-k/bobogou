package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.entity.other.Vip;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.other.UserMemberMapper;
import com.littlebuddha.bobogou.modules.mapper.other.VipMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomerUserService extends CrudService<CustomerUser, CustomerUserMapper> {

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private VipMapper vipMapper;

    @Resource
    private UserMemberMapper userMemberMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public CustomerUser get(CustomerUser entity) {
        CustomerUser customerUser = super.get(entity);
        if (customerUser != null && StringUtils.isNotBlank(customerUser.getHeader())) {
            customerUser.setHeader(globalSetting.getRootPath() + customerUser.getHeader());
        }
        return customerUser;
    }

    public CustomerUser getByPhone(CustomerUser entity) {
        CustomerUser byPhone = customerUserMapper.getByPhone(entity);
        return byPhone;
    }

    /**
     * ??????operator??????
     *
     * @param selectOption
     * @return
     */
    public CustomerUser findByOperator(CustomerUser selectOption) {
        CustomerUser customerUser = customerUserMapper.getByOperator(selectOption);
        return customerUser;
    }

    @Override
    public List<CustomerUser> findList(CustomerUser entity) {
        return super.findList(entity);
    }

    /**
     * ??????????????????----------?????????????????????????????????????????????????????????----?????????????????????
     *
     * @param page
     * @param entity
     * @return
     */
    @Override
    public PageInfo<CustomerUser> findPage(Page<CustomerUser> page, CustomerUser entity) {
        //??????????????????
        if (entity != null) {
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setPhone(phone);
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            entity.setNickname(nickname);
        }
        //?????????????????????????????????????????????????????????????????????----?????????????????????
        PageInfo<CustomerUser> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<CustomerUser> list = customerUserMapper.findListData(entity);
            pageInfo = new PageInfo<CustomerUser>(list);
        }
        return pageInfo;
    }

    /**
     * ??????????????????????????????
     *
     * @param page
     * @param entity
     * @return
     */
    public PageInfo<CustomerUser> findVipOverStayedPage(Page<CustomerUser> page, CustomerUser entity) {
        //??????????????????
        if (entity != null) {
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setPhone(phone);
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            entity.setNickname(nickname);
        }
        //??????????????????????????????
        PageInfo<CustomerUser> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<CustomerUser> list = customerUserMapper.findVipOverStayedData(entity);
            pageInfo = new PageInfo<CustomerUser>(list);
        }
        return pageInfo;
    }

    @Override
    public int save(CustomerUser entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(CustomerUser entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(CustomerUser entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<CustomerUser> findRecoveryPage(Page<CustomerUser> page, CustomerUser entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(CustomerUser entity) {
        return super.recovery(entity);
    }

    @Transactional
    public int beVip(CustomerUser customerUser) {
        if (customerUser.getUserMember() != null && customerUser.getUserMember() != null) {
            //??????????????????????????????????????????????????????????????????vip?????????????????????VIP??????
            Operator currentUser = UserUtils.getCurrentUser();
            if (currentUser.getAreaManager() == 4 || currentUser.getAreaManager() == 5 && customerUser.getApplyStatus() == 2) {
                //???????????????????????????????????????????????????vip??????????????????vip??????
                String type = customerUser.getUserMember().getType();
                Vip vip = new Vip();
                vip.setType(Integer.valueOf(type));
                Vip byType = vipMapper.getByType(vip);
                if (byType != null && byType.getTime() != null) {
                    Date specifyDate = DateUtils.getSpecifyDate(byType.getTime());
                    String fullDate = DateUtils.getFullDate(specifyDate);
                    customerUser.setVipExpire(fullDate);
                }
            }
            //?????????????????????????????????
            //????????????????????????areaManager????????????????????????id
            String currentUserParentRoleId = UserUtils.getCurrentUserParentRoleId();
            customerUser.setNextRole(currentUserParentRoleId);
            //??????????????????????????????userMember??????
            UserMember userMember = userMemberMapper.get(customerUser.getUserMember());
            //??????currentUser???????????????????????????????????????
            CustomerUser selectOption = new CustomerUser();
            selectOption.setOperatorId(currentUser.getId());
            CustomerUser byOperator = customerUserMapper.getByOperator(selectOption);
            if (byOperator != null) {
                if (byOperator.getAreaManager() == 1) {
                    userMember.setProvinceUserId(currentUser.getId());
                } else if (byOperator.getAreaManager() == 2) {
                    userMember.setCityUserId(currentUser.getId());
                } else if (byOperator.getAreaManager() == 3) {
                    userMember.setDistrictUserId(currentUser.getId());
                }
            }
            //?????????????????????CustomerUser
            //????????????????????????
            userMember.setStatus(customerUser.getUserMember().getStatus());
            //????????????????????????
            userMember.setProvinceRefuseReason(customerUser.getUserMember().getProvinceRefuseReason());
            userMember.setProvincePassReason(customerUser.getUserMember().getProvincePassReason());
            userMember.setCityRefuseReason(customerUser.getUserMember().getCityRefuseReason());
            userMember.setCityPassReason(customerUser.getUserMember().getCityPassReason());
            userMember.setDistrictRefuseReason(customerUser.getUserMember().getDistrictRefuseReason());
            userMember.setDistrictPassReason(customerUser.getUserMember().getDistrictPassReason());
            userMemberMapper.update(userMember);
        }
        int row = customerUserMapper.beVip(customerUser);
        return row;
    }

    /**
     * ??????????????????(???????????????????????????)
     *
     * @param
     * @param
     * @return
     */
    public PageInfo<CustomerUser> findToDoDataPage(Page<CustomerUser> page, CustomerUser entity) {
        //if(entity != null){
        //    String phone = StringUtils.deleteWhitespace(entity.getPhone());
        //    entity.setPhone(phone);
        //    String nickname = StringUtils.deleteWhitespace(entity.getNickname());
        //    entity.setNickname(nickname);
        //}
        PageInfo<CustomerUser> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<CustomerUser> list = customerUserMapper.getToDoList(entity);
            pageInfo = new PageInfo<CustomerUser>(list);
        }
        return pageInfo;
    }

    /**
     * ??????????????????(??????????????????)
     *
     * @param
     * @param
     * @return
     */
    public PageInfo<CustomerUser> findVipApplyForAreaManager(Page<CustomerUser> page, CustomerUser entity) {
        Operator currentUser = UserUtils.getCurrentUser();
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        List<CustomerUser> result = new ArrayList<>();
        PageInfo<CustomerUser> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            //??????????????????????????????????????????????????????????????????????????????????????????
            if (operatorRegionList != null && !operatorRegionList.isEmpty()) {
                for (OperatorRegion region : operatorRegionList) {
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<CustomerUser> vipApplyForAreaManager = customerUserMapper.getVipApplyForAreaManager(entity);
                        if (vipApplyForAreaManager != null) {
                            result.addAll(vipApplyForAreaManager);
                        }
                    }
                }
                //???result??????
                if (!result.isEmpty()) {
                    //??????
                    for (int i = 0; i < result.size() - 1; i++) {
                        for (int j = result.size() - 1; j > i; j--) {
                            if (result.get(j).getId().equals(result.get(i).getId())) {
                                result.remove(j);
                            }
                        }
                    }
                }
            }
            List list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            if (result != null && !result.isEmpty()) {
                pageInfo = new PageInfo<CustomerUser>();
                pageInfo.setList(list);
                pageInfo.setTotal(result.size());
            }else {
                pageInfo = new PageInfo<>();
            }
        }
        return pageInfo;
    }

    /**
     * ????????????
     *
     * @param customerUser
     * @return
     */
    public int recoveryVip(CustomerUser customerUser) {
        int row = customerUserMapper.recoveryVip(customerUser);
        return row;
    }

    /**
     * ???????????????????????????????????????app??????----????????????????????????
     * @return
     */
    public List<CustomerUser> findCurrentUserSubordinateArea(CustomerUser entity){
        //??????????????????
        List<CustomerUser> result = new ArrayList<>();
        //????????????????????????
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //?????????????????????????????????list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //??????????????????
        PageInfo<CustomerUser> pageInfo = null;
        //??????????????????????????????????????????????????????
        if (currentUser != null && "1".equals(currentUser.getId())){//?????????????????????????????????
            result = customerUserMapper.findList(entity);
        }else {
            for (OperatorRegion region : operatorRegionList) {//???????????????????????????
                if (region != null) {
                    entity.setProvinceId(region.getProvinceId());
                    entity.setCityId(region.getCityId());
                    entity.setAreaId(region.getDistrictId());
                    entity.setStreetId(region.getStreetId());
                    List<CustomerUser> royaltyRecordList = customerUserMapper.findList(entity);
                    if (royaltyRecordList != null) {
                        result.addAll(royaltyRecordList);
                    }
                }
            }
        }
        //??????
        result = ListUtils.removeDuplicateCustomerUser(result);
        return result;
    }
}
