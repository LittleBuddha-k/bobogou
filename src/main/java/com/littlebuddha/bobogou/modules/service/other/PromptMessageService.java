package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.PromptMessage;
import com.littlebuddha.bobogou.modules.mapper.other.PromptMessageMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PromptMessageService extends CrudService<PromptMessage, PromptMessageMapper> {

    @Override
    public PromptMessage get(PromptMessage entity) {
        return super.get(entity);
    }

    @Override
    public List<PromptMessage> findList(PromptMessage entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<PromptMessage> findPage(Page<PromptMessage> page, PromptMessage entity) {
        if(entity != null){
            String title = StringUtils.deleteWhitespace(entity.getTitle());
            entity.setTitle(title);
            String outLine = StringUtils.deleteWhitespace(entity.getOutline());
            entity.setOutline(outLine);
        }
        return super.findPage(page, entity);
    }

    @Override
    public int save(PromptMessage entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(PromptMessage entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(PromptMessage entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<PromptMessage> findRecoveryPage(Page<PromptMessage> page, PromptMessage entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(PromptMessage entity) {
        return super.recovery(entity);
    }
}
