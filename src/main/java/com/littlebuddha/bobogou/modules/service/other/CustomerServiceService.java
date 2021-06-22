package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.CustomerService;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerServiceMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomerServiceService extends CrudService<CustomerService, CustomerServiceMapper> {

    @Override
    public CustomerService get(CustomerService entity) {
        return super.get(entity);
    }

    @Override
    public List<CustomerService> findList(CustomerService entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<CustomerService> findPage(Page<CustomerService> page, CustomerService entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(CustomerService entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(CustomerService entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(CustomerService entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<CustomerService> findRecoveryPage(Page<CustomerService> page, CustomerService entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(CustomerService entity) {
        return super.recovery(entity);
    }
}
