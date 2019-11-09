package com.atguigu.gmall.pms.service.impl;

import com.atguigu.pmall.pms.vo.SpuAttributeValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.ProductAttrValueDao;
import com.atguigu.pmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    ProductAttrValueDao productAttrValueDao;

    /*
     * @Description   根据spuId查询检索属性及值
     * @Date   2019/11/7 16:54
     */
    @Override
    public List<SpuAttributeValueVO> querySearchAttrValue(Long spuId) {

        List<ProductAttrValueEntity> productAttrValueEntities = this.productAttrValueDao.querySearchAttrValue(spuId);

        return productAttrValueEntities.stream().map(productAttrValueEntity -> {
            SpuAttributeValueVO spuAttributeValueVO = new SpuAttributeValueVO();
            spuAttributeValueVO.setProductAttributeId(productAttrValueEntity.getAttrId());
            spuAttributeValueVO.setName(productAttrValueEntity.getAttrName());
            spuAttributeValueVO.setValue(productAttrValueEntity.getAttrValue());
            return spuAttributeValueVO;
        }).collect(Collectors.toList());
    }



    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageVo(page);
    }

}