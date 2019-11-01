package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 商品三级分类
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageVo queryPage(QueryCondition params);

    /**
     * 商品分类
     * @param level
     * @param parentCid
     * @return
     */
    List<CategoryEntity> queryCategory(Integer level, Long parentCid);

}

