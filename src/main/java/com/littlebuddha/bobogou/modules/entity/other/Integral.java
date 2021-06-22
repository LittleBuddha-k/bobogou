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
 * 积分实体类
 */
public class Integral extends DataEntity<Integral> {

    private Integer integral;//积分
    private Integer day;//第几天
    private String accountId;//最后操作人ID

    public Integral() {
    }

    public Integral(String id) {
        super(id);
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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
