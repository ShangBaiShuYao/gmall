package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;



/*
 * @Description 远程调用
 * @Date   2019/11/15 20:07
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}


/*

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi
{


}
*/
