package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 *文件上传实体类
 */
public class Contract extends DataEntity<Contract> {

    private Integer type;//类型，1=药房合同，2=诊所合同，3=医院合同
    private String content;//合同内容，富文本
    private String operatorId;//操作人ID

    public Contract() {
    }

    public Contract(String id) {
        super(id);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperatorId() {
        if (createBy != null && StringUtils.isNotBlank(createBy.getId())){
            operatorId = createBy.getId();
        }
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
