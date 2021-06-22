package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-22
 * \* Time: 下午 02:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * 系统消息实体类
 */
public class PromptMessage extends DataEntity<System> {

    private String title;//标题
    private String outline;//概要信息
    private String content;//内容
    private Integer type;//类型，0=系统消息，1=促销活动消息
    private Integer userId;//用户ID，推送给所有用户=0
    private String accountId;//最后操作人

    public PromptMessage() {
    }

    public PromptMessage(String id) {
        super(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        if(updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
