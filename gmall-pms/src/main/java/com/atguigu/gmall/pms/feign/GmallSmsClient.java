package com.atguigu.gmall.pms.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.vo.SaleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





/*
 @Auther: 上白书妖
 * @Date: 2019/11/5 16:38
 * @Description:
*/


@FeignClient("sms-service")
public interface GmallSmsClient  {

    @PostMapping("sms/skubounds/skusale/save")
    Resp<Object> saveSale(@RequestBody SaleVO saleVO);

}

/*@FeignClient("sms-service")
public interface GmallSmsClient
{

    @PostMapping("/sms/skubounds/skusale/save")
    public Resp<Object> saveSkuSaleInfo(@RequestBody SkuSaleDTO skuSaleDTO);
}*/
