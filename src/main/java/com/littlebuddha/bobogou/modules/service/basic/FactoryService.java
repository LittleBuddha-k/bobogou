package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.basic.FactoryMapper;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import com.littlebuddha.bobogou.modules.mapper.data.CityMapper;
import com.littlebuddha.bobogou.modules.mapper.data.ProvinceMapper;
import com.littlebuddha.bobogou.modules.mapper.data.StreetMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("factory")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FactoryService extends CrudService<Factory, FactoryMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Resource
    private FactoryMapper factoryMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private StreetMapper streetMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Override
    public Factory get(Factory entity) {
        Factory factory = factoryMapper.get(entity);
        if (factory != null && StringUtils.isNotBlank(factory.getProvinceId())){
            Province province = provinceMapper.get(new Province(factory.getProvinceId()));
            factory.setProvince(province);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getCityId())){
            City city = cityMapper.get(new City(factory.getCityId()));
            factory.setCity(city);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getAreaId())){
            Area area = areaMapper.get(new Area(factory.getAreaId()));
            factory.setArea(area);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getStreetId())){
            Street street = streetMapper.get(new Street(factory.getStreetId()));
            factory.setStreet(street);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getCardFront())){
            String cardFront = factory.getCardFront();
            String aa = "";
            String[] split = cardFront.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setCardFront(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getCardBack())){
            String cardBack = factory.getCardBack();
            String aa = "";
            String[] split = cardBack.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setCardBack(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getBusinessLicense())){
            String businessLicense = factory.getBusinessLicense();
            String aa = "";
            String[] split = businessLicense.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setBusinessLicense(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getAnnualReport())){
            String annualReport = factory.getAnnualReport();
            String aa = "";
            String[] split = annualReport.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setAnnualReport(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getBusinessPermit())){
            String businessPermit = factory.getBusinessPermit();
            String aa = "";
            String[] split = businessPermit.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setBusinessPermit(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getBasicAccount())){
            String basicAccount = factory.getBasicAccount();
            String aa = "";
            String[] split = basicAccount.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setBasicAccount(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getBillingInformation())){
            String billingInformation = factory.getBillingInformation();
            String aa = "";
            String[] split = billingInformation.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setBillingInformation(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getSampleInvoiceTicket())){
            String sampleInvoiceTicket = factory.getSampleInvoiceTicket();
            String aa = "";
            String[] split = sampleInvoiceTicket.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setSampleInvoiceTicket(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getQualityGuarantee())){
            String qualityGuarantee = factory.getQualityGuarantee();
            String aa = "";
            String[] split = qualityGuarantee.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setQualityGuarantee(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getSealImpression())){
            String sealImpression = factory.getSealImpression();
            String aa = "";
            String[] split = sealImpression.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setSealImpression(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getPowerAttorney())){
            String powerAttorney = factory.getPowerAttorney();
            String aa = "";
            String[] split = powerAttorney.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setPowerAttorney(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getInvoiceCounterparts())){
            String invoiceCounterparts = factory.getInvoiceCounterparts();
            String aa = "";
            String[] split = invoiceCounterparts.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setInvoiceCounterparts(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getBailorCard())){
            String bailorCard = factory.getBailorCard();
            String aa = "";
            String[] split = bailorCard.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setBailorCard(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getMandataryCard())){
            String mandataryCard = factory.getMandataryCard();
            String aa = "";
            String[] split = mandataryCard.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setMandataryCard(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getTakeDeliveryBailment())){
            String takeDeliveryBailment = factory.getTakeDeliveryBailment();
            String aa = "";
            String[] split = takeDeliveryBailment.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setTakeDeliveryBailment(aa);
        }
        if (factory != null && StringUtils.isNotBlank(factory.getFoodBusinessLicense())){
            String foodBusinessLicense = factory.getFoodBusinessLicense();
            String aa = "";
            String[] split = foodBusinessLicense.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factory.setFoodBusinessLicense(aa);
        }
        return factory;
    }

    @Override
    public List<Factory> findList(Factory entity) {
        List<Factory> list = super.findList(entity);
        for (Factory factory : list) {
            if (factory != null && StringUtils.isNotBlank(factory.getCardFront())){
                String cardFront = factory.getCardFront();
                String aa = "";
                String[] split = cardFront.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setCardFront(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getCardBack())){
                String cardBack = factory.getCardBack();
                String aa = "";
                String[] split = cardBack.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setCardBack(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getBusinessLicense())){
                String businessLicense = factory.getBusinessLicense();
                String aa = "";
                String[] split = businessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setBusinessLicense(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getAnnualReport())){
                String annualReport = factory.getAnnualReport();
                String aa = "";
                String[] split = annualReport.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setAnnualReport(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getBusinessPermit())){
                String businessPermit = factory.getBusinessPermit();
                String aa = "";
                String[] split = businessPermit.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setBusinessPermit(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getBasicAccount())){
                String basicAccount = factory.getBasicAccount();
                String aa = "";
                String[] split = basicAccount.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setBasicAccount(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getBillingInformation())){
                String billingInformation = factory.getBillingInformation();
                String aa = "";
                String[] split = billingInformation.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setBillingInformation(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getSampleInvoiceTicket())){
                String sampleInvoiceTicket = factory.getSampleInvoiceTicket();
                String aa = "";
                String[] split = sampleInvoiceTicket.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setSampleInvoiceTicket(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getQualityGuarantee())){
                String qualityGuarantee = factory.getQualityGuarantee();
                String aa = "";
                String[] split = qualityGuarantee.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setQualityGuarantee(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getSealImpression())){
                String sealImpression = factory.getSealImpression();
                String aa = "";
                String[] split = sealImpression.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setSealImpression(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getPowerAttorney())){
                String powerAttorney = factory.getPowerAttorney();
                String aa = "";
                String[] split = powerAttorney.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setPowerAttorney(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getInvoiceCounterparts())){
                String invoiceCounterparts = factory.getInvoiceCounterparts();
                String aa = "";
                String[] split = invoiceCounterparts.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setInvoiceCounterparts(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getBailorCard())){
                String bailorCard = factory.getBailorCard();
                String aa = "";
                String[] split = bailorCard.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setBailorCard(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getMandataryCard())){
                String mandataryCard = factory.getMandataryCard();
                String aa = "";
                String[] split = mandataryCard.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setMandataryCard(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getTakeDeliveryBailment())){
                String takeDeliveryBailment = factory.getTakeDeliveryBailment();
                String aa = "";
                String[] split = takeDeliveryBailment.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setTakeDeliveryBailment(aa);
            }
            if (factory != null && StringUtils.isNotBlank(factory.getFoodBusinessLicense())){
                String foodBusinessLicense = factory.getFoodBusinessLicense();
                String aa = "";
                String[] split = foodBusinessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factory.setFoodBusinessLicense(aa);
            }
        }
        return list;
    }

    @Override
    public PageInfo<Factory> findPage(Page<Factory> page, Factory entity) {
        if(entity != null){
            entity.setFactoryName(StringUtils.deleteWhitespace(entity.getFactoryName()));
        }
        //封装的结果集
        List<Factory> result = new ArrayList<>();
        //获取当前用户区域
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //查询封装结果
        PageInfo<Factory> pageInfo = null;
        //根据当前用户区域查询对应发货单位数据
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            if (currentUser != null && "1".equals(currentUser.getId())){//如果是超级管理员查询全部数据
                result = factoryMapper.findList(entity);
            }else {
                for (OperatorRegion region : operatorRegionList) {//按照区域来进行查询
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<Factory> factoryList = factoryMapper.findList(entity);
                        if (factoryList != null) {
                            result.addAll(factoryList);
                        }
                    }
                }
            }
            //去重
            result = ListUtils.removeDuplicateFactory(result);
            //格式化图片路径
            for (Factory factory : result) {
                if (factory != null && StringUtils.isNotBlank(factory.getCardFront())){
                    String cardFront = factory.getCardFront();
                    String aa = "";
                    String[] split = cardFront.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setCardFront(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getCardBack())){
                    String cardBack = factory.getCardBack();
                    String aa = "";
                    String[] split = cardBack.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setCardBack(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getBusinessLicense())){
                    String businessLicense = factory.getBusinessLicense();
                    String aa = "";
                    String[] split = businessLicense.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setBusinessLicense(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getAnnualReport())){
                    String annualReport = factory.getAnnualReport();
                    String aa = "";
                    String[] split = annualReport.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setAnnualReport(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getBusinessPermit())){
                    String businessPermit = factory.getBusinessPermit();
                    String aa = "";
                    String[] split = businessPermit.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setBusinessPermit(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getBasicAccount())){
                    String basicAccount = factory.getBasicAccount();
                    String aa = "";
                    String[] split = basicAccount.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setBasicAccount(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getBillingInformation())){
                    String billingInformation = factory.getBillingInformation();
                    String aa = "";
                    String[] split = billingInformation.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setBillingInformation(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getSampleInvoiceTicket())){
                    String sampleInvoiceTicket = factory.getSampleInvoiceTicket();
                    String aa = "";
                    String[] split = sampleInvoiceTicket.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setSampleInvoiceTicket(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getQualityGuarantee())){
                    String qualityGuarantee = factory.getQualityGuarantee();
                    String aa = "";
                    String[] split = qualityGuarantee.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setQualityGuarantee(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getSealImpression())){
                    String sealImpression = factory.getSealImpression();
                    String aa = "";
                    String[] split = sealImpression.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setSealImpression(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getPowerAttorney())){
                    String powerAttorney = factory.getPowerAttorney();
                    String aa = "";
                    String[] split = powerAttorney.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setPowerAttorney(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getInvoiceCounterparts())){
                    String invoiceCounterparts = factory.getInvoiceCounterparts();
                    String aa = "";
                    String[] split = invoiceCounterparts.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setInvoiceCounterparts(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getBailorCard())){
                    String bailorCard = factory.getBailorCard();
                    String aa = "";
                    String[] split = bailorCard.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setBailorCard(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getMandataryCard())){
                    String mandataryCard = factory.getMandataryCard();
                    String aa = "";
                    String[] split = mandataryCard.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setMandataryCard(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getTakeDeliveryBailment())){
                    String takeDeliveryBailment = factory.getTakeDeliveryBailment();
                    String aa = "";
                    String[] split = takeDeliveryBailment.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setTakeDeliveryBailment(aa);
                }
                if (factory != null && StringUtils.isNotBlank(factory.getFoodBusinessLicense())){
                    String foodBusinessLicense = factory.getFoodBusinessLicense();
                    String aa = "";
                    String[] split = foodBusinessLicense.split(",");
                    for (String image : split) {
                        aa = aa + globalSetting.getRootPath() + image + ",";
                    }
                    factory.setFoodBusinessLicense(aa);
                }
            }
            //分页数据
            List list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            //组装结果
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

    /**
     * 用于词典选择用---查询所有
     * @return
     */
    public List<Factory> findToDictUse(){
        Factory entity = new Factory();
        List<Factory> list = super.findList(entity);
        return list;
    }

    @Override
    public int save(Factory entity) {
        if(StringUtils.isNotBlank(entity.getCardFront())){
            entity.setCardFront(entity.getCardFront().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getCardBack())){
            entity.setCardBack(entity.getCardBack().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getBusinessLicense())){
            entity.setBusinessLicense(entity.getBusinessLicense().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getAnnualReport())){
            entity.setAnnualReport(entity.getAnnualReport().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getBusinessPermit())){
            entity.setBusinessPermit(entity.getBusinessPermit().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getBasicAccount())){
            entity.setBasicAccount(entity.getBasicAccount().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getBillingInformation())){
            entity.setBillingInformation(entity.getBillingInformation().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getSampleInvoiceTicket())){
            entity.setSampleInvoiceTicket(entity.getSampleInvoiceTicket().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getQualityGuarantee())){
            entity.setQualityGuarantee(entity.getQualityGuarantee().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getSealImpression())){
            entity.setSealImpression(entity.getSealImpression().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getPowerAttorney())){
            entity.setPowerAttorney(entity.getPowerAttorney().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getInvoiceCounterparts())){
            entity.setInvoiceCounterparts(entity.getInvoiceCounterparts().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getBailorCard())){
            entity.setBailorCard(entity.getBailorCard().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getMandataryCard())){
            entity.setMandataryCard(entity.getMandataryCard().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getTakeDeliveryBailment())){
            entity.setTakeDeliveryBailment(entity.getTakeDeliveryBailment().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getFoodBusinessLicense())){
            entity.setFoodBusinessLicense(entity.getFoodBusinessLicense().replaceAll(globalSetting.getRootPath(),""));
        }
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Factory entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Factory entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Factory> findRecoveryPage(Page<Factory> page, Factory entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Factory entity) {
        return super.recovery(entity);
    }
}
