package com.atguigu.gmall.pms.vo;

import com.alibaba.nacos.client.utils.StringUtils;
import com.atguigu.pmall.pms.entity.ProductAttrValueEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/4 19:35
 * @Description:
 */
public class ProductAttrValueVO extends ProductAttrValueEntity {

    public void setValueSelected(List<Object> valueSelected){
        // 如果接受的集合为空，则不设置
        if (CollectionUtils.isEmpty(valueSelected)){
            return;
        }
        this.setAttrValue(StringUtils.join(valueSelected, ","));
    }
}