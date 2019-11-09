package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.vo.AttrGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.pmall.pms.entity.AttrGroupEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 属性分组
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageVo queryPage(QueryCondition params);




    /*
    * @Description 查询分组下的规格参数
    * @Date   2019/11/3 13:54
    */
    AttrGroupVO queryById(Long gid);

    /**
     * 查询规格分组
     * @param cid
     * @param condition
     * @return
     */
    PageVo queryByCidPage(Long cid, QueryCondition condition);
}

