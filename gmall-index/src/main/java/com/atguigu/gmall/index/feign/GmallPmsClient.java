package com.atguigu.gmall.index.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/16 23:58
 * @Description:
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi
{

}