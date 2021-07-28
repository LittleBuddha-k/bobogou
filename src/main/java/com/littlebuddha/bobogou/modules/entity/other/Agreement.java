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
 * 协议实体类
 */
public class Agreement extends DataEntity<Agreement> {

    private Integer type;//类型，1=用户协议，2=积分规则，3=VIP规则
    private String title;//标题
    private String content = "";//内容----已转化为html
    private String contentEdit = "";//回显编辑时需要使用
    private String accountId;//最后操作人ID

    public Agreement() {
    }

    public Agreement(String id) {
        super(id);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentEdit() {
        return contentEdit;
    }

    public void setContentEdit(String contentEdit) {
        this.contentEdit = contentEdit;
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
