package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
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
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    ProductAttrValueDao productAttrValueDao;

    /*
     * @Description   根据spuId查询检索属性及值
     * 这是两表关联查询是否是检索属性的
     * @Date   2019/11/7 16:54
     */
    @Override
    public List<SpuAttributeValueVO> querySearchAttrValue(Long spuId) {

        List<ProductAttrValueEntity> productAttrValueEntities = this.productAttrValueDao.querySearchAttrValue(spuId);

        /*
         * @Description 使用stream表达式将将一个集合转换为另外一个集合
         * @Date   2019/11/14 20:34
         */
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