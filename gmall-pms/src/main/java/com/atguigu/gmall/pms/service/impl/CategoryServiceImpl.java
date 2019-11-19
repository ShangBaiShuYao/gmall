package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.CategoryDao;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /**
     * 商品分类
     */
    @Autowired
    private CategoryDao categoryDao;

  /*  @Autowired
   CategoryDao categoryDao;*/


    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageVo(page);
    }


    /**
     * 商品分类
     * @param level
     * @param parentCid
     * @return
     */
    @Override
    public List<CategoryEntity> queryCategory(Integer level, Long parentCid)
    {
        //构造条件查询
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        //如果level级别是0,说明查询所有的级别
        if (level != 0)
        {
            wrapper.eq("cat_level", level);
        }
        //如果parentCid为null,说明用户没有查询该字段,查询所有
        if (parentCid != null)
        {
            wrapper.eq("parent_Cid",parentCid);
        }

         return this.categoryDao.selectList(wrapper);
    }

    /*
     * @Description
     * @Date   2019/11/17 1:19
     */
    @Override
    public List<CategoryVO> queryCategoryWithSub(Long pid) {

        List<CategoryVO> categoryVO = this.categoryDao.queryCategoryWithSub(pid);

        return categoryVO;
    }

}