package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;

import java.util.List;


/**
 *  @作者  上白书妖
 *  @Date  2019/11/14 0:04
 *  @Email shangbaishuyao@163.com
 *  @Description 
 */ 
@Data
public class GroupVO {

    private String groupName;

    private List<ProductAttrValueEntity> baseAttrValues;
}