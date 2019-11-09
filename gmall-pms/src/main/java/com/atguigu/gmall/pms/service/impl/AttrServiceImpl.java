package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.pmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.vo.AttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.pmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {


    /*
     * @Description 保存规格参数
     * @Date   2019/11/3 14:16
     */
    @Autowired
    private AttrDao attrDao;

    /*
     * @Description 保存规格参数
     * @Date   2019/11/3 14:17
     */
    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageVo(page);
    }


    /*
     * @Description 查询规格参数
     * @Date   2019/11/3 14:11
     */
    @Override
    public PageVo queryByCidTypePage(QueryCondition queryCondition, Long cid, Integer type) {
        // 构建查询条件
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", cid);
        if (type != null) {
            wrapper.eq("attr_type", type);
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(queryCondition), // 构建分页条件
                wrapper
        );

        return new PageVo(page);
    }



    /*
     * @Description 保存规格参数
     * @Date   2019/11/3 14:11
     */
    @Transactional
    @Override
    public void saveAttrVO(AttrVO attrVO) {
        // 新增规格参数
        this.attrDao.insert(attrVO);

        // 新增中间表
        AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
        relation.setAttrId(attrVO.getAttrId());
        relation.setAttrGroupId(attrVO.getAttrGroupId());
        this.relationDao.insert(relation);
    }

}