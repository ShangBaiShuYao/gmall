package com.atguigu.gmall.pms.vo;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/4 19:33
 * @Description:
 */

import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * spuInfo扩展对象
 * 包含：spuInfo基本信息、spuImages图片信息、baseAttrs基本属性信息、skus信息
 */
@Data
public class SpuInfoVO extends SpuInfoEntity
{

    // 图片信息
    private List<String> spuImages;

    // 基本属性信息
    private List<ProductAttrValueVO> baseAttrs;

    // sku信息
    private List<SkuInfoVO> skus;
}