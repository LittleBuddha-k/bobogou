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
     * 通过operator查找
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
     * 返回列表数据----------只有质管员、超级管理员助理可以查看数据----超级管理员除外
     *
     * @param page
     * @param entity
     * @return
     */
    @Override
    public PageInfo<CustomerUser> findPage(Page<CustomerUser> page, CustomerUser entity) {
        //列表查询条件
        if (entity != null) {
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setPhone(phone);
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            entity.setNickname(nickname);
        }
        //根据查询只有质管员、超级管理员助理可以查看数据----超级管理员除外
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
     * 返回会员已过期的数据
     *
     * @param page
     * @param entity
     * @return
     */
    public PageInfo<CustomerUser> findVipOverStayedPage(Page<CustomerUser> page, CustomerUser entity) {
        //列表查询条件
        if (entity != null) {
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setPhone(phone);
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            entity.setNickname(nickname);
        }
        //查询会员已过期的数据
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
            //当超级管理员或者超级管理员助理通过之后才表示vip生效，再去设置VIP时效
            Operator currentUser = UserUtils.getCurrentUser();
            if (currentUser.getAreaManager() == 4 || currentUser.getAreaManager() == 5 && customerUser.getApplyStatus() == 2) {
                //根据用户申请资料中的类型字段，查询vip规则表，设定vip时效
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
            //获取当前角色的父级角色
            //根据当前审核人的areaManager设置对应的审核人id
            String currentUserParentRoleId = UserUtils.getCurrentUserParentRoleId();
            customerUser.setNextRole(currentUserParentRoleId);
            //这里同意过后执行更新userMember数据
            UserMember userMember = userMemberMapper.get(customerUser.getUserMember());
            //根据currentUser查询当前用户在前端表的数据
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
            //获取当前用户的CustomerUser
            //设置对应审核状态
            userMember.setStatus(customerUser.getUserMember().getStatus());
            //设置对应审核意见
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
     * 查询待办数据(区级管理以外的数据)
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
     * 查询待办数据(区级管理数据)
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
            //当当前用户设置了区域，根据其的区域数据来获取其下的待审核数据
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
                //对result去重
                if (!result.isEmpty()) {
                    //去重
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
     * 恢复会员
     *
     * @param customerUser
     * @return
     */
    public int recoveryVip(CustomerUser customerUser) {
        int row = customerUserMapper.recoveryVip(customerUser);
        return row;
    }

    /**
     * 查询当前登录用户区域的下级app用户----用于做选择下拉框
     * @return
     */
    public List<CustomerUser> findCurrentUserSubordinateArea(CustomerUser entity){
        //封装的结果集
        List<CustomerUser> result = new ArrayList<>();
        //获取当前用户区域
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //查询封装结果
        PageInfo<CustomerUser> pageInfo = null;
        //根据当前用户区域查询对应发货单位数据
        if (currentUser != null && "1".equals(currentUser.getId())){//超级管理员查询所有数据
            result = customerUserMapper.findList(entity);
        }else {
            for (OperatorRegion region : operatorRegionList) {//按照区域来进行查询
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
        //去重
        result = ListUtils.removeDuplicateCustomerUser(result);
        return result;
    }
}
