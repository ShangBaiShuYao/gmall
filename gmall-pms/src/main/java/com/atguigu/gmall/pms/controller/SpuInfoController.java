package com.atguigu.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.pmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.service.SpuInfoService;





/*
 *  @作者  上白书妖
 *  @Date  2019/11/7 16:31
 *  @Email shangbaishuyao@163.com
 *  @Description  spu信息
 */
@Api(tags = "spu信息 管理")
@RestController
@RequestMapping("pms/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /*
     * @Description 分页查询已上架SPU
     * @Date   2019/11/7 16:30
     */
    @ApiOperation("分页查询已发布spu商品信息")
    @PostMapping("{status}")
    public Resp<List<SpuInfoEntity>> querySpuInfoByStatus(@RequestBody QueryCondition condition , @PathVariable("status") Integer status  )
    {
        IPage<SpuInfoEntity> spuInfoEntityIPage = this.spuInfoService.page(new Query<SpuInfoEntity>().getPage(condition), new QueryWrapper<SpuInfoEntity>().eq("publish_status", status));
        return Resp.ok(spuInfoEntityIPage.getRecords());
    }



    /*
     * @Description 查询商品列表
     * @Date   2019/11/3 14:25
     */
    @ApiOperation("spu商品信息查询")
    @GetMapping
    public Resp<PageVo> querySpuInfo(QueryCondition condition, @RequestParam("catId")Long catId){

        PageVo page = this.spuInfoService.querySpuInfo(condition, catId);
        return Resp.ok(page);
    }


    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('pms:spuinfo:list')")
    public Resp<PageVo> querySpuPage(@RequestBody QueryCondition queryCondition) {
        PageVo page = spuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }



    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spuinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:spuinfo:info')")
    public Resp<SpuInfoEntity> info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return Resp.ok(spuInfo);
    }

    /**
     * 保存
     */
/*    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuinfo:save')")
    public Resp<Object> save(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.save(spuInfo);

        return Resp.ok(null);
    }*/
    /**
     * 保存
     */
    /*
     * @Description  完成SPU新增功能
     * @Date   2019/11/4 19:41
     */
/*    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuinfo:save')")
    public Resp<Object> save(@RequestBody SpuInfoVO spuInfoVO){

        spuInfoService.bigSave(spuInfoVO);
        return Resp.ok(null);
    }*/



    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuinfo:save')")
    public Resp<Object> save(@RequestBody SpuInfoVO spuInfoVO) {
        spuInfoService.bigSave(spuInfoVO);

        return Resp.ok(null);
    }


    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spuinfo:update')")
    public Resp<Object> update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spuinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
