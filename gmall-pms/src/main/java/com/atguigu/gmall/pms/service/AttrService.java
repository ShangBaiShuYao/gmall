package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.vo.AttrVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.pmall.pms.entity.AttrEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品属性
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-11-01 20:49:24
 */
public interface AttrService extends IService<AttrEntity> {

    PageVo queryPage(QueryCondition params);

    /*
     * @Description 查询规格参数
     * @Date   2019/11/3 14:10
     */
    PageVo queryByCidTypePage(QueryCondition queryCondition, Long cid, Integer type);


    /*
     * @Description 保存规格参数
     * @Date   2019/11/3 14:10
     */
    void saveAttrVO(AttrVO attrVO);
}

