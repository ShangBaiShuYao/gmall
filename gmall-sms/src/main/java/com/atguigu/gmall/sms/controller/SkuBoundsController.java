package com.atguigu.gmall.sms.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.dto.SkuSaleDTO;
import com.atguigu.gmall.sms.service.SkuBoundsService;
import com.atguigu.gmall.vo.SaleVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/3 17:15
 * @Description:
 */
@RestController("sms")
public class SkuBoundsController
{
    @Autowired
    private SkuBoundsService skuBoundsService;

    @PostMapping("sale")
    public Resp<Object> saveSale(@RequestBody SaleVO saleVO)
    {
        this.skuBoundsService.saveSale(saleVO);
        return Resp.ok(null);
    }

    @ApiOperation("新增sku的营销信息")
    @PostMapping("/skusale/save")
    public Resp<Object> saveSkuSaleInfo(@RequestBody SkuSaleDTO skuSaleDTO){
        this.skuBoundsService.saveSkuSaleInfo(skuSaleDTO);

        return Resp.ok(null);
    }

}