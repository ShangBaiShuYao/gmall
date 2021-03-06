package com.atguigu.core.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  @作者  上白书妖
 *  @Date  2019/11/4 11:55
 *  @Email shangbaishuyao@163.com
 *  @Description  Request URL: http://127.0.0.1:8888/pms/spuinfo? t=1572839396320 & page=1 & limit=10 & key= & catId=0
 */
@ApiModel
@Data
public class QueryCondition {


    @ApiModelProperty(name = "page",value = "页码",required = false)
    private Long page;

    @ApiModelProperty(name = "limit",value = "每页大小",required = false)
    private Long limit;

    @ApiModelProperty(name = "sidx",value = "排序的字段",required = false)
    private String sidx;

    @ApiModelProperty(name = "order",value = "排序的顺序",required = false)
    private String order;

    @ApiModelProperty(name = "asc",value = "系统默认排序",required = false)
    private String asc = "asc";
    
    
    /*
     * @Description  spu商品信息查询
     * @Date   2019/11/3 14:32
     */
    private String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
