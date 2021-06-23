package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Sticker;
import com.littlebuddha.bobogou.modules.mapper.other.StickerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StickerService extends CrudService<Sticker, StickerMapper> {

    @Override
    public Sticker get(Sticker entity) {
        return super.get(entity);
    }

    @Override
    public List<Sticker> findList(Sticker entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Sticker> findPage(Page<Sticker> page, Sticker entity) {
        if(entity != null){
            String theHair = StringUtils.deleteWhitespace(entity.getTheHeir());
            entity.setTheHeir(theHair);
        }
        return super.findPage(page, entity);
    }

    @Override
    public int save(Sticker entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Sticker entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Sticker entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Sticker> findRecoveryPage(Page<Sticker> page, Sticker entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Sticker entity) {
        return super.recovery(entity);
    }
}
