package com.atguigu.gmall.item.vo;

import lombok.Data;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 18:50
 * @Description:
 */
@Data
public class BaseAttrVO {

    private Long attrId;
    private String attrName;
    private String[]  attrValues;
}