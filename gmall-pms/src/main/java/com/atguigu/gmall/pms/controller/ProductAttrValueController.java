package com.atguigu.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.service.ProductAttrValueService;





/*
 *  @作者  上白书妖
 *  @Date  2019/11/8 23:22
 *  @Email shangbaishuyao@163.com
 *  @Description  spu属性值
 */
@Api(tags = "spu属性值 管理")
@RestController
@RequestMapping("pms/productattrvalue")
public class ProductAttrValueController
{

    @Autowired
    private ProductAttrValueService productAttrValueService;


    /*
     * @Description 根据spuId查询检索属性及值
     * @Date   2019/11/8 23:11
     */
  /*  public Resp<List<ProductAttrValueVO>> querySearchAttrValue(@PathVariable("{spuId}")Long spuId)
    {
        List<ProductAttrValueEntity> attrValueVOS = this.productAttrValueService.querySearchAttrValue(spuId);
        return Resp.ok(attrValueVOS);
    }*/





  /*
   * @Description 这个两表关联查询,根据spuid查询sku但是无法判断sku是是检索属性,所以我们就两表关联查询,
   * 但是没有两表关联查询的方法,所以我们就自己写这个方法
   * 写好的查询是否是检索属性的SQL语句已经放入XMl配置文件中了
   *
   * 但是这里的返回值应该返回什么呢?
   *   返回一个List<SpuAttributeValueVO>的对象,但是不只一个微服务需要,所以 我么提取出来放入interface工程中
   * @Date   2019/11/8 23:21
   */
    @ApiOperation("根据spuId查询检索属性及值")
    @GetMapping("/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId")Long spuId){

        List<SpuAttributeValueVO> attrValueVOS = this.productAttrValueService.querySearchAttrValue(spuId);
        return Resp.ok(attrValueVOS);
    }


    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:productattrvalue:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = productAttrValueService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:productattrvalue:info')")
    public Resp<ProductAttrValueEntity> info(@PathVariable("id") Long id){
		ProductAttrValueEntity productAttrValue = productAttrValueService.getById(id);

        return Resp.ok(productAttrValue);
    }


    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:productattrvalue:save')")
    public Resp<Object> save(@RequestBody ProductAttrValueEntity productAttrValue){
		productAttrValueService.save(productAttrValue);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:productattrvalue:update')")
    public Resp<Object> update(@RequestBody ProductAttrValueEntity productAttrValue){
		productAttrValueService.updateById(productAttrValue);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:productattrvalue:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		productAttrValueService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
