package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品属性
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-11-01 20:49:24
 */
public interface AttrService extends IService<AttrEntity> {

    PageVo queryPage(QueryCondition params);

    /**
     * 查询规格参数
     * @param queryCondition
     * @param cid
     * @param type
     * @return
     */
    PageVo queryByCidTypePage(QueryCondition queryCondition, Long cid, Integer type);
}

