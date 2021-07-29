package com.littlebuddha.bobogou.modules.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.Qualification;
import com.littlebuddha.bobogou.modules.mapper.basic.QualificationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QualificationService extends CrudService<Qualification, QualificationMapper> {

    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public Qualification get(Qualification entity) {
        Qualification qualification = qualificationMapper.get(entity);
        StringJoiner sj = new StringJoiner(",");
        if (qualification != null && StringUtils.isNotBlank(qualification.getQualification())){
            String[] split = qualification.getQualification().split(",");
            for (String s : split) {
                sj.add(globalSetting.getRootPath() + s);
            }
            qualification.setQualification(sj.toString());
        }
        return qualification;
    }

    @Override
    public List<Qualification> findList(Qualification entity) {
        List<Qualification> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<Qualification> findPage(Page<Qualification> page, Qualification entity) {
        PageInfo<Qualification> page1 = super.findPage(page, entity);
        return page1;
    }

    public PageInfo<Qualification> findTodoPage(Page<Qualification> page, Qualification entity) {
        /*if (entity != null && StringUtils.isNotBlank(entity.getPartAName())){
            entity.setPartAName(StringUtils.deleteWhitespace(entity.getPartAName()));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getPartBName())){
            entity.setPartBName(StringUtils.deleteWhitespace(entity.getPartBName()));
        }*/
        PageInfo<Qualification> pageInfo = null;
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
            List<Qualification> list = qualificationMapper.findTodoList(entity);
            pageInfo = new PageInfo<Qualification>(list);
        }
        return pageInfo;
    }

    public PageInfo<Qualification> findDownloadDataPage(Page<Qualification> page, Qualification entity) {
        PageInfo<Qualification> pageInfo = null;
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
            List<Qualification> list = qualificationMapper.findDownloadDataPage(entity);
            pageInfo = new PageInfo<Qualification>(list);
        }
        return pageInfo;
    }

    @Override
    public int save(Qualification entity) {
        if (entity != null && entity.getQualification() != null){
            entity.setQualification(entity.getQualification().replaceAll(globalSetting.getRootPath(),""));
        }
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(Qualification entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Qualification entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Qualification> findRecoveryPage(Page<Qualification> page, Qualification entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Qualification entity) {
        return super.recovery(entity);
    }
}
