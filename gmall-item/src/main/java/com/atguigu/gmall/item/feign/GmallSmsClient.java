package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.pms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 23:59
 * @Description:
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {
}