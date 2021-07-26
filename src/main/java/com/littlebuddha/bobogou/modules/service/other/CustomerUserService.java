package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
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

import java.util.Date;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomerUserService extends CrudService<CustomerUser, CustomerUserMapper> {

    @Autowired
    private CustomerUserMapper customerUserMapper;

    @Autowired
    private VipMapper vipMapper;

    @Autowired
    private UserMemberMapper userMemberMapper;

    @Autowired
    private OperatorRegionMapper operatorRegionMapper;

    @Override
    public CustomerUser get(CustomerUser entity) {
        return super.get(entity);
    }

    public CustomerUser getByPhone(CustomerUser entity){
        CustomerUser byPhone = customerUserMapper.getByPhone(entity);
        return byPhone;
    }

    /**
     * 通过operator查找
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

    @Override
    public PageInfo<CustomerUser> findPage(Page<CustomerUser> page, CustomerUser entity) {
        if(entity != null){
            String phone = StringUtils.deleteWhitespace(entity.getPhone());
            entity.setPhone(phone);
            String nickname = StringUtils.deleteWhitespace(entity.getNickname());
            entity.setNickname(nickname);
        }
        return super.findPage(page, entity);
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
        if (customerUser.getUserMember() != null && customerUser.getUserMember() != null){
            //当超级管理员或者超级管理员助理通过之后才表示vip生效，再去设置VIP时效
            //根据当前审核人的areaManager设置对应的审核人id
            Operator currentUser = UserUtils.getCurrentUser();
            if (currentUser.getAreaManager() == 4 || currentUser.getAreaManager() == 5 && customerUser.getApplyStatus() == 2){
                //根据用户申请资料中的类型字段，查询vip规则表，设定vip时效
                Integer type = customerUser.getUserMember().getType();
                Vip vip = new Vip();
                vip.setType(type);
                Vip byType = vipMapper.getByType(vip);
                if (byType != null && byType.getTime() != null){
                    Date specifyDate = DateUtils.getSpecifyDate(byType.getTime());
                    String fullDate = DateUtils.getFullDate(specifyDate);
                    customerUser.setVipExpire(fullDate);
                }
            }
            //获取当前角色的父级角色
            String currentUserParentRoleId = UserUtils.getCurrentUserParentRoleId();
            customerUser.setNextRole(currentUserParentRoleId);
            //这里同意过后执行更新userMember数据
            UserMember userMember = userMemberMapper.get(customerUser.getUserMember());
            //根据currentUser查询当前用户在前端表的数据
            CustomerUser selectOption = new CustomerUser();
            selectOption.setOperatorId(currentUser.getId());
            CustomerUser byOperator = customerUserMapper.getByOperator(selectOption);
            if (byOperator != null){
                if (byOperator.getAreaManager() == 1){
                    userMember.setProvinceUserId(currentUser.getId());
                }else if (byOperator.getAreaManager() == 2){
                    userMember.setCityUserId(currentUser.getId());
                }else if (byOperator.getAreaManager() == 3){
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
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
            List<CustomerUser> list = customerUserMapper.getToDoList(entity);
            pageInfo = new PageInfo<CustomerUser>(list);
        }
        return pageInfo;
    }

    /**
     * 查询待办数据(区级管理数据)
     * @param
     * @param
     * @return
     */
    public PageInfo<CustomerUser> findVipApplyForAreaManager(Page<CustomerUser> page, CustomerUser entity) {
        Operator currentUser = UserUtils.getCurrentUser();
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> operatorRegions = operatorRegionMapper.findList(operatorRegion);
        String provinceIds = "";
        String cityIds = "";
        String areaIds = "";
        String streetIds = "";
        if (operatorRegions != null){
            for (OperatorRegion region : operatorRegions) {
                provinceIds = region.getProvinceId() + "," + provinceIds;
                cityIds = region.getCityId() + "," + cityIds;
                areaIds = region.getAreaId() + "," + areaIds;
                streetIds = region.getStreetId() + "," + streetIds;
            }
            provinceIds = provinceIds.substring(0,provinceIds.length() - 1);
            cityIds = cityIds.substring(0,cityIds.length() - 1);
            areaIds = areaIds.substring(0,areaIds.length() - 1);
            streetIds = streetIds.substring(0,streetIds.length() - 1);
        }
        PageInfo<CustomerUser> pageInfo = null;
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
            List<CustomerUser> list = customerUserMapper.getVipApplyForAreaManager(provinceIds,cityIds,areaIds,streetIds);
            pageInfo = new PageInfo<CustomerUser>(list);
        }
        return pageInfo;
    }
}
