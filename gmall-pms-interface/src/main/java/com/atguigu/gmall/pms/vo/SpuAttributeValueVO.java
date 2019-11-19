package com.atguigu.gmall.pms.vo;

import lombok.Data;

/*
 * @Auther: 上白书妖
 * @Date: 2019/11/8 21:03
 * @Description: 基本属性,这是课检索的索性,(销售属性不需要检索的)
 *
 * 检索属性的id,将所属性的名称,检索属性的值
 */
@Data
public class SpuAttributeValueVO
{
    private Long productAttributeId; //当前sku对应的属性的attr_id
    private String name;//属性名  电池
    private String value;//3G   3000mah
}