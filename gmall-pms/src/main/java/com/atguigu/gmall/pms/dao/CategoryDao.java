package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
@Mapper
@Repository
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    /**
     * 商品分类
     * @param wrapper
     * @return
     */
    List<CategoryEntity> selectList(QueryWrapper<Object> wrapper);
}
