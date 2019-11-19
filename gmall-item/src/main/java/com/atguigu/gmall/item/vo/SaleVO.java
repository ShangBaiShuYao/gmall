package com.atguigu.gmall.item.vo;

import lombok.Data;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 18:34
 * @Description:
 */
@Data
public class SaleVO {

    // 0-优惠券    1-满减    2-阶梯
    private Integer type;

    private String name;//促销信息/优惠券的名字

}