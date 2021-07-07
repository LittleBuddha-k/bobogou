package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Agreement;
import com.littlebuddha.bobogou.modules.mapper.other.AgreementMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgreementService extends CrudService<Agreement, AgreementMapper> {

    @Autowired
    private AgreementMapper agreementMapper;

    @Override
    public Agreement get(Agreement entity) {
        return super.get(entity);
    }

    @Override
    public List<Agreement> findList(Agreement entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Agreement> findPage(Page<Agreement> page, Agreement entity) {
        if(entity != null){
            String title = StringUtils.deleteWhitespace(entity.getTitle());
            entity.setTitle(title);
        }
        return super.findPage(page, entity);
    }

    @Override
    public int save(Agreement entity) {
        int save = 0;
        entity.setIdType("AUTO");
        //每个类型的协议只能存在一条
        //1.根据所传值得类型查询数据
        Agreement findByType = agreementMapper.getByType(entity);
        //1.1若是新增，根据类型去查询数据库是否存在此类型数据
        if (StringUtils.isBlank(entity.getId())){
            if (entity.getType() != null && findByType != null){
                //1.2若存在，返回已经存在同类型数据，不可插入第二条同类型数据
                save = -1;
            }else if (entity.getType() != null && findByType == null){
                //1.2若不存在，则执行新增
                save = super.save(entity);
            }
        }
        //2.若是编辑。根据类型查询数据，并比较新旧两条数据的类型是否一致
        if (StringUtils.isNotBlank(entity.getId())){
            //2.1根据编辑的类型找到了一条，但是找到的一条数据id与我编辑的一条数据id不一致
            if (findByType != null && !findByType.getId().equals(entity.getId())){
                save = -1;
            }else if (findByType != null && findByType.getId().equals(entity.getId())){
                //2.2根据编辑的类型找到了一条，且找到的一条数据id与我编辑的一条数据id一致
                save = super.save(entity);
            }else if (findByType == null){
                //2.2如果并不存在此类型数据，则继续执行修改
                save = super.save(entity);
            }
        }
        return save;
    }

    @Override
    public int deleteByLogic(Agreement entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Agreement entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Agreement> findRecoveryPage(Page<Agreement> page, Agreement entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Agreement entity) {
        return super.recovery(entity);
    }
}
