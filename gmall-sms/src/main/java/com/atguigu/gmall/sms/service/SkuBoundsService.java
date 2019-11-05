package com.atguigu.gmall.sms.service;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.sms.dto.SkuSaleDTO;
import com.atguigu.gmall.sms.entity.SkuBoundsEntity;
import com.atguigu.gmall.vo.SaleVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SkuBoundsService extends IService<SkuBoundsEntity> {

    PageVo queryPage(QueryCondition params);

    void saveSale(SaleVO saleVO);

    void saveSkuSaleInfo(SkuSaleDTO skuSaleDTO);


}

