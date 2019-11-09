package com.atguigu.gmall.pms.dao;

import com.atguigu.pmall.pms.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * spu属性值
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {

    /*
     * @Description 根据spuId查询检索属性及值
     * @Date   2019/11/7 16:54
     */
    List<ProductAttrValueEntity> querySearchAttrValue(Long spuId);
}
