package com.atguigu.gmall.pms.vo;

import com.atguigu.pmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.pmall.pms.entity.AttrEntity;
import com.atguigu.pmall.pms.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * 查询分组下的规格参数
 * @Auther: 上白书妖
 * @Date: 2019/11/2 00:45
 * @Description:
 */
@Data
public class AttrGroupVO extends AttrGroupEntity {

    private List<AttrEntity> attrEntities;

    private List<AttrAttrgroupRelationEntity> relations;

    public List<AttrEntity> getAttrEntities() {
        return attrEntities;
    }

    public void setAttrEntities(List<AttrEntity> attrEntities) {
        this.attrEntities = attrEntities;
    }

    public List<AttrAttrgroupRelationEntity> getRelations() {
        return relations;
    }

    public void setRelations(List<AttrAttrgroupRelationEntity> relations) {
        this.relations = relations;
    }
}