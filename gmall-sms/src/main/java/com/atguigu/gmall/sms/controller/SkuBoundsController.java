package com.atguigu.gmall.sms.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.dto.SkuSaleDTO;
import com.atguigu.gmall.sms.service.SkuBoundsService;
import com.atguigu.gmall.sms.vo.SaleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/3 17:15
 * @Description:
 */
@Api(tags = "商品sku积分设置 管理")
@RestController
@RequestMapping("sms/skubounds")
public class SkuBoundsController
{
    @Autowired
    private SkuBoundsService skuBoundsService;

    @PostMapping("sale")
    public Resp<Object> saveSale(@RequestBody SaleVO saleVO)
    {
        try {
            this.skuBoundsService.saveSale(saleVO);
        } catch (FileExistsException e) {
            e.printStackTrace();
        }
        return Resp.ok(null);
    }

    @ApiOperation("新增sku的营销信息")
    @PostMapping("/skusale/save")
    public Resp<Object> saveSkuSaleInfo(@RequestBody SkuSaleDTO skuSaleDTO){
        this.skuBoundsService.saveSkuSaleInfo(skuSaleDTO);

        return Resp.ok(null);
    }

}