package com.atguigu.gmall.pms.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.atguigu.gmall.pms.dao.ProductAttrValueDao;
import com.atguigu.gmall.pms.dao.SkuInfoDao;
import com.atguigu.gmall.pms.dao.SpuInfoDescDao;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.ProductAttrValueVO;
import com.atguigu.gmall.pms.vo.SkuInfoVO;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.SpuInfoDao;
import com.atguigu.gmall.pms.service.SpuInfoService;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService
{



    @Autowired
    private SpuInfoDescDao descDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;


    @Autowired
    private SkuInfoDao skuInfoDao;


//    @Autowired
//    private GmallSmsClient gmallSmsClient;


    /*
     * @Description 查询商品列表
     * @Date   2019/11/3 14:27
     */
    @Override
    public PageVo querySpuInfo(QueryCondition condition, Long catId) {
        // 封装分页条件
        IPage<SpuInfoEntity> page = new Query<SpuInfoEntity>().getPage(condition);

        // 封装查询条件
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        // 如果分类id不为0，要根据分类id查，否则查全部
        if (catId != 0){
            wrapper.eq("catalog_id", catId);
        }
        // 如果用户输入了检索条件，根据检索条件查
        String key = condition.getKey();
        if (StringUtils.isNotBlank(key)){
            wrapper.and(t -> t.like("spu_name", key).or().like("id", key));
        }

        return new PageVo(this.page(page, wrapper));
    }

    /*
     * @Description 完成SPU新增功能
     * @Date   2019/11/4 19:42
     */

    /**
     * 九张表：
     * 1.spu相关：3
     * 2.sku相关：3
     * 3.营销相关：3
     * @param spuInfoVO
     */
//    @GlobalTransactional
//    @Transactional
    @Override
    public void bigSave(SpuInfoVO spuInfoVO) {

        // 1.新增spu相关的3张表
        // 1.1. 新增spuInfo
        spuInfoVO.setCreateTime(new Date());
        spuInfoVO.setUodateTime(spuInfoVO.getCreateTime());
        this.save(spuInfoVO);
        Long spuId = spuInfoVO.getId();

        // 1.2. 新增spuInfoDesc
//        this.saveSpuDesc(spuInfoVO, spuId);
        List<String> spuImages = spuInfoVO.getSpuImages();
        String desc = StringUtils.join(spuImages, ",");
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuId);
        descEntity.setDecript(desc);
        this.descDao.insert(descEntity);
        //this.spuInfoDescService.saveSpuDesc(spuInfoVO, spuId);

        // 1.3. 新增基本属性productAttrValue
        List<ProductAttrValueVO> baseAttrs = spuInfoVO.getBaseAttrs();
        baseAttrs.forEach(baseAttr -> {
            baseAttr.setSpuId(spuId);
            baseAttr.setAttrSort(0);
            baseAttr.setQuickShow(1);
            this.productAttrValueDao.insert(baseAttr);
        });

        //2.新增sku相关三张表,spuId
        List<SkuInfoVO> skus = spuInfoVO.getSkus();
        if (CollectionUtils.isEmpty(skus)){
            return;
        }
        for (SkuInfoVO skuInfoVO : skus) {// 2.1. 新增skuInfo
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(skuInfoVO, skuInfoEntity);
            skuInfoEntity.setBrandId(spuInfoVO.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoVO.getCatalogId());
            skuInfoEntity.setSkuCode(UUID.randomUUID().toString());
            skuInfoEntity.setSpuId(spuId);
            List<String> images = skuInfoVO.getImages();
            // 设置默认图片
            if (!CollectionUtils.isEmpty(images)) {
                skuInfoEntity.setSkuDefaultImg(StringUtils.isNotBlank(skuInfoEntity.getSkuDefaultImg()) ? skuInfoEntity.getSkuDefaultImg() : images.get(0));
            }
            this.skuInfoDao.insert(skuInfoEntity);
            Long skuId = skuInfoEntity.getSkuId();





            /*
             * @Description 新增部分 ,前面的代码没有问题
             * @Date   2019/11/4 23:13
             */

            // 2.2. 新增sku的图片
/*
            if (!CollectionUtils.isEmpty(images)) {
                images.forEach(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setDefaultImg(StringUtils.equals(image, skuInfoEntity.getSkuDefaultImg()) ? 1 : 0);
                    skuImagesEntity.setImgSort(0);
                    skuImagesEntity.setImgUrl(image);
                    this.skuImagesDao.insert(skuImagesEntity);
                });
            }

            */
/*
             * @Description 新增销售属性
             * @Date   2019/11/4 23:15
             *//*

            List<SkuSaleAttrValueEntity> saleAttrs = skuInfoVO.getSaleAttrs();
            if (!CollectionUtils.isEmpty(saleAttrs)) {
                saleAttrs.forEach(saleAttr -> {
                    saleAttr.setSkuId(skuId);
                    saleAttr.setAttrSort(0);
                    this.skuSaleAttrValueDao.insert(saleAttr);
                });
            }


            // 3.新增营销相关的3张表 skuId
            BeanUtils.copyProperties(skuInfoVO, saleVO);
            saleVO.setSkuId(skuId);
            this.gmallSmsClient.saveSale(saleVO);
*/


        }
    }


            @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }


}