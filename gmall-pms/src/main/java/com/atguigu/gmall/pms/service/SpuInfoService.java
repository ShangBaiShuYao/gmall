package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * spu信息
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageVo queryPage(QueryCondition params);

    /*
     * @Description 查询商品列表
     * @Date   2019/11/3 14:25
     */
    PageVo querySpuInfo(QueryCondition condition, Long catId);


    /*
     * @Description  完成SPU新增功能
     * @Date   2019/11/5 20:13
     */
    void bigSave(SpuInfoVO spuInfoVO);
}

