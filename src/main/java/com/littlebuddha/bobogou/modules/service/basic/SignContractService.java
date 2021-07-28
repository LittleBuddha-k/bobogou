package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.SignContract;
import com.littlebuddha.bobogou.modules.mapper.basic.SignContractMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SignContractService extends CrudService<SignContract, SignContractMapper> {

    @Autowired
    private SignContractMapper signContractMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public SignContract get(SignContract entity) {
        SignContract signContract = signContractMapper.get(entity);
        if (signContract != null && StringUtils.isNotBlank(signContract.getFrontIdCard())){
            signContract.setFrontIdCard(globalSetting.getRootPath() + signContract.getFrontIdCard());
        }
        if (signContract != null && StringUtils.isNotBlank(signContract.getBackIdCard())){
            signContract.setBackIdCard(globalSetting.getRootPath() + signContract.getBackIdCard());
        }
        if (signContract != null && StringUtils.isNotBlank(signContract.getQualification())){
            StringJoiner sj = new StringJoiner(",");
            String[] split = signContract.getQualification().split(",");
            for (String s : split) {
                s = globalSetting.getRootPath() + s;
                sj.add(s);
            }
            signContract.setQualification(sj.toString());
        }
        if (signContract != null && StringUtils.isNotBlank(signContract.getContract())){
            StringJoiner sj = new StringJoiner(",");
            String[] split = signContract.getContract().split(",");
            for (String s : split) {
                s = globalSetting.getRootPath() + s;
                sj.add(s);
            }
            signContract.setContract(sj.toString());
        }
        return signContract;
    }

    @Override
    public List<SignContract> findList(SignContract entity) {
        List<SignContract> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<SignContract> findPage(Page<SignContract> page, SignContract entity) {
        if (entity != null && StringUtils.isNotBlank(entity.getPartAName())){
            entity.setPartAName(StringUtils.deleteWhitespace(entity.getPartAName()));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getPartBName())){
            entity.setPartBName(StringUtils.deleteWhitespace(entity.getPartBName()));
        }
        PageInfo<SignContract> page1 = super.findPage(page, entity);
        return page1;
    }

    @Override
    public int save(SignContract entity) {
        if (entity != null && StringUtils.isNotBlank(entity.getFrontIdCard())){
            entity.setFrontIdCard(entity.getFrontIdCard().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getBackIdCard())){
            entity.setBackIdCard(entity.getBackIdCard().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getQualification())){
            entity.setQualification(entity.getQualification().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getContract())){
            entity.setContract(entity.getContract().replaceAll(globalSetting.getRootPath(),""));
        }
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(SignContract entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(SignContract entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<SignContract> findRecoveryPage(Page<SignContract> page, SignContract entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(SignContract entity) {
        return super.recovery(entity);
    }
}
