package com.atguigu.gmall.item.vo;


import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.pms.vo.ItemSaleVO;
import lombok.Data;

import java.util.List;
@Data
public class ItemVO extends SkuInfoEntity {

    private BrandEntity brand;
    private CategoryEntity category;
    private SpuInfoEntity spuInfo;

    private List<String> pics; // sku的图片列表

    private List<ItemSaleVO> sales; // 营销信息

    private Boolean store; // 是否有货

    private List<SkuSaleAttrValueEntity> skuSales; // spu下所有sku的销售属性

    private SpuInfoDescEntity desc; // 描述信息

    private List<GroupVO> groups; // 组及组下的规格属性及值

}



/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 18:30
 * @Description:
 */
/*
@Data
public class ItemVO {

    //1、当前sku的基本信息
    private Long skuId;
    private Long spuId;
    private Long catalogId;
    private Long brandId;
    private String skuTitle;
    private String skuSubtitle;
    private BigDecimal price;
    private BigDecimal weight;
    private BrandEntity brand;

    private CategoryEntity category;
    private SpuInfoEntity spuInfo;
    //2、sku的所有图片
    private List<String> pics;

    //3、sku的所有促销信息
    private List<ItemSaleVO> sales; // 营销信息

    //4、sku的所有销售属性组合
    private List<SkuSaleAttrValueEntity> saleAttrs;

    //5、spu的所有基本属性
    private List<BaseGroupVO> attrGroups;

    //6、详情介绍
    private SpuInfoDescEntity desc;


    private Boolean store; // 是否有货

    private List<SkuSaleAttrValueEntity> skuSales; // spu下所有sku的销售属性

    private List<GroupVO> groups; // 组及组下的规格属性及值

    public void setBracnd(BrandEntity data) {
    }

}*/
