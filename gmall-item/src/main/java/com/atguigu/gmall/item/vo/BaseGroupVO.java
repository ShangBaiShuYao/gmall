package com.atguigu.gmall.item.vo;

import lombok.Data;

import java.util.List;

/**
 * 基本属性分组及组下的规格参数
 */
@Data
public class BaseGroupVO {

    private Long id;
    private String name;//分组的名字
    private List<BaseAttrVO> attrs;
}