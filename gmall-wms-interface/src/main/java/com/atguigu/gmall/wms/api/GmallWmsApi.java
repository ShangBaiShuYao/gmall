package com.atguigu.gmall.wms.api;


import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
 *  @作者  上白书妖
 *  @Date  2019/11/9 0:33
 *  @Email shangbaishuyao@163.com
 *  @Description
 */
public interface GmallWmsApi
{
    /*
     * @Description 根据Id查询库存
     * @Date   2019/11/9 0:33
     */
    @GetMapping("wms/waresku/{skuId}")
    public Resp<List<WareSkuEntity>> queryWareSkuBySkuId(@PathVariable("skuId")Long skuId);
}
