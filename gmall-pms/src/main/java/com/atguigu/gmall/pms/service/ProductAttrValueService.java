package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * spu属性值
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    
    /*
     * @Description
     * @Date   2019/11/8 23:29
     */
    PageVo queryPage(QueryCondition params);

    /*
     * @Description 根据spuId查询检索属性及值
     * @Date   2019/11/7 16:45
     */
    List<SpuAttributeValueVO> querySearchAttrValue(Long spuId);
}

