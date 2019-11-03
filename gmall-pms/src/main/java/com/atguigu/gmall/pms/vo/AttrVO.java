package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.AttrEntity;
import lombok.Data;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/3 14:07
 * @Description: 保存规格参数
 */
@Data
public class AttrVO extends AttrEntity
{

    private Long attrGroupId;

    public Long getAttrGroupId() {
        return attrGroupId;
    }

    public void setAttrGroupId(Long attrGroupId) {
        this.attrGroupId = attrGroupId;
    }
}