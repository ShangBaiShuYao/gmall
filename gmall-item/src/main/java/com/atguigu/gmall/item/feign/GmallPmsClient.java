package com.atguigu.gmall.item.feign;


import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 23:55
 * @Description:
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi
{


}