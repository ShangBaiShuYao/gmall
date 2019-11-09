package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.pmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 属性&属性分组关联
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:48
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageVo queryPage(QueryCondition params);

    /*
     * @Description 删除关联关系
     * @Date   2019/11/3 13:58
     */
    void delete(List<AttrAttrgroupRelationEntity> relationEntities);
}

