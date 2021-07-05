package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Chat;
import com.littlebuddha.bobogou.modules.mapper.other.ChatMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChatService extends CrudService<Chat, ChatMapper> {

    @Override
    public Chat get(Chat entity) {
        return super.get(entity);
    }

    @Override
    public List<Chat> findList(Chat entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Chat> findPage(Page<Chat> page, Chat entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Chat entity) {
        return super.save(entity);
    }
}
