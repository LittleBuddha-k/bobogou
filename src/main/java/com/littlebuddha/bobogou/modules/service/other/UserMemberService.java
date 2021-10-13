package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.mapper.other.UserMemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserMemberService extends CrudService<UserMember, UserMemberMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Resource
    private UserMemberMapper userMemberMapper;

    @Override
    public UserMember get(UserMember entity) {
        UserMember userMember = super.get(entity);
        if (userMember != null && StringUtils.isNotBlank(userMember.getBusinessLicense())) {
            String businessLicense = userMember.getBusinessLicense();
            String aa = "";
            String[] split = businessLicense.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setBusinessLicense(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getBusinessCertificate())) {
            String businessCertificate = userMember.getBusinessCertificate();
            String aa = "";
            String[] split = businessCertificate.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setBusinessCertificate(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getFoodBusinessLicense())) {
            String foodBusinessLicense = userMember.getFoodBusinessLicense();
            String aa = "";
            String[] split = foodBusinessLicense.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setFoodBusinessLicense(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getAuthorityPurchase())) {
            String authorityPurchase = userMember.getAuthorityPurchase();
            String aa = "";
            String[] split = authorityPurchase.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setAuthorityPurchase(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getMandatary())) {
            String mandatary = userMember.getMandatary();
            String aa = "";
            String[] split = mandatary.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setMandatary(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getGroupPhoto())) {
            String groupPhoto = userMember.getGroupPhoto();
            String aa = "";
            String[] split = groupPhoto.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setGroupPhoto(aa);
        }
        if (userMember != null && StringUtils.isNotBlank(userMember.getContractUrl())) {
            String contractUrl = userMember.getContractUrl();
            String aa = "";
            String[] split = contractUrl.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            userMember.setContractUrl(aa);
        }
        return userMember;
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
        if (entity != null && StringUtils.isNotBlank(entity.getBusinessLicense())) {
            entity.setBusinessLicense(entity.getBusinessLicense().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getBusinessCertificate())) {
            entity.setBusinessCertificate(entity.getBusinessCertificate().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getFoodBusinessLicense())) {
            entity.setFoodBusinessLicense(entity.getFoodBusinessLicense().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getAuthorityPurchase())) {
            entity.setAuthorityPurchase(entity.getAuthorityPurchase().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getMandatary())) {
            entity.setMandatary(entity.getMandatary().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getGroupPhoto())) {
            entity.setGroupPhoto(entity.getGroupPhoto().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getContractUrl())) {
            entity.setContractUrl(entity.getContractUrl().replaceAll(globalSetting.getRootPath(), ""));
        }
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
     *默认只取最新创建时间的userMember数据
     * @param userMember
     * @return
     */
    public UserMember getByUser(UserMember userMember) {
        //-----默认只取最新创建时间的一条数据
        List<UserMember> memberList = userMemberMapper.getByUser(userMember);
        if (memberList != null && !memberList.isEmpty()){
            UserMember entity = memberList.get(0);
            if (entity != null && StringUtils.isNotBlank(entity.getBusinessLicense())) {
                String businessLicense = entity.getBusinessLicense();
                String aa = "";
                String[] split = businessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setBusinessLicense(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getBusinessCertificate())) {
                String businessCertificate = entity.getBusinessCertificate();
                String aa = "";
                String[] split = businessCertificate.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setBusinessCertificate(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getFoodBusinessLicense())) {
                String foodBusinessLicense = entity.getFoodBusinessLicense();
                String aa = "";
                String[] split = foodBusinessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setFoodBusinessLicense(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getAuthorityPurchase())) {
                String authorityPurchase = entity.getAuthorityPurchase();
                String aa = "";
                String[] split = authorityPurchase.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setAuthorityPurchase(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getMandatary())) {
                String mandatary = entity.getMandatary();
                String aa = "";
                String[] split = mandatary.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setMandatary(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getGroupPhoto())) {
                String groupPhoto = entity.getGroupPhoto();
                String aa = "";
                String[] split = groupPhoto.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setGroupPhoto(aa);
            }
            if (entity != null && StringUtils.isNotBlank(entity.getContractUrl())) {
                String contractUrl = entity.getContractUrl();
                String aa = "";
                String[] split = contractUrl.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                entity.setContractUrl(aa);
            }
            return entity;
        }else {
            return null;
        }
    }

    public int updateVipStatus(UserMember userMember) {
        int row = userMemberMapper.updateVipStatus(userMember);
        return  row;
    }
}
