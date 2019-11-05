package com.atguigu.gmall.pms.feign;

import com.atguigu.gmall.feign.GmallSmsApi;
import com.atguigu.gmall.vo.SaleVO;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/5 16:38
 * @Description:
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {
    void saveSale(SaleVO saleVO);
}