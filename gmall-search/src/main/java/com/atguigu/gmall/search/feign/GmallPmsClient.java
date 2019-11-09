package com.atguigu.gmall.search.feign;

import com.atguigu.pmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi
{
}
