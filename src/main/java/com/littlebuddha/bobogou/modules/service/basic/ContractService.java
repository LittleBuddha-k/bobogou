package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.Contract;
import com.littlebuddha.bobogou.modules.mapper.basic.ContractMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContractService extends CrudService<Contract, ContractMapper> {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public Contract get(Contract entity) {
        Contract contract = contractMapper.get(entity);
        if (contract != null && StringUtils.isNotBlank(contract.getContent())){
            String content = contract.getContent();
            String aa = "";
            String[] split = content.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            contract.setContent(aa);
        }
        return contract;
    }

    @Override
    public List<Contract> findList(Contract entity) {
        List<Contract> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<Contract> findPage(Page<Contract> page, Contract entity) {
        PageInfo<Contract> page1 = super.findPage(page, entity);
        return page1;
    }

    @Override
    public int save(Contract entity) {
        if (entity != null && StringUtils.isNotBlank(entity.getContent())){
            entity.setContent(entity.getContent().replaceAll(globalSetting.getRootPath(),""));
        }
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(Contract entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Contract entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Contract> findRecoveryPage(Page<Contract> page, Contract entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Contract entity) {
        return super.recovery(entity);
    }
}
