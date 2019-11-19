package com.atguigu.gmall.search.feign;


import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;


/*
 * @Description  远程调用 就是这个接口伪装成 wms-service配置的服务,继承GmallWmsApi接口,调用你想调用的方法实现干某事的能力
 * @Date   2019/11/15 20:07
 */
@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}

/*import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi
{

}*/
