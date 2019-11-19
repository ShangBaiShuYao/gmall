package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.vo.AttrGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }




    /**
     *  @作者  上白书妖
     *  @Date  2019/11/3 13:44
     *  @Email shangbaishuyao@163.com
     *  @Description 查询分组下的规格参数
     */
    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrDao attrDao;

    @Override
    public AttrGroupVO queryById(Long gid) {

        // 查询分组
        AttrGroupVO attrGroupVO = new AttrGroupVO();
        AttrGroupEntity attrGroupEntity = this.attrGroupDao.selectById(gid);
        BeanUtils.copyProperties(attrGroupEntity, attrGroupVO);

        // 查询分组下的关联关系
        List<AttrAttrgroupRelationEntity> relations = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        // 判断关联关系是否为空，如果为空，直接返回
        if (CollectionUtils.isEmpty(relations)){
            return attrGroupVO;
        }
        attrGroupVO.setRelations(relations);

        // 收集分组下的所有规格id
        List<Long> attrIds = relations.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
        // 查询分组下的所有规格参数
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);
        attrGroupVO.setAttrEntities(attrEntities);

        return attrGroupVO;
    }




    /**
     * 查询规格分组
     *
     * 请求参数：一些基本分页条件已经封装到QueryCondition，然后有一个占位符参数（商品分类：225）
     * @param cid
     * @param condition
     * @作者 上白书妖
     * @return
     */
    @Override
    public PageVo queryByCidPage(Long cid, QueryCondition condition)
    {

        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(condition),
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid)
        );
        return new PageVo(page);
    }

    @Override
    public List<AttrGroupVO> queryGroupWithAttrsByCid(Long catId) {

        // 根据分类查询分类下的所有组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));

        // 查询每个组下的所有规格参数
        return groupEntities.stream().map(attrGroupEntity -> this.queryGroupWithAttrs(attrGroupEntity.getAttrGroupId())).collect(Collectors.toList());
    }
        public AttrGroupVO queryGroupWithAttrs(Long gid) {

            AttrGroupVO groupVO = new AttrGroupVO();

            // 先查询分组
            AttrGroupEntity groupEntity = this.getById(gid);
            BeanUtils.copyProperties(groupEntity, groupVO);

            // 根据分组id查询关联关系
            List<AttrAttrgroupRelationEntity> relationEntities = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
            if (CollectionUtils.isEmpty(relationEntities)){
                return groupVO;
            }
        groupVO.setRelations(relationEntities);

        // 根据关联关系的attrId查询属性
        List<Long> attrIds = relationEntities.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);
        groupVO.setAttrEntities(attrEntities);

        return groupVO;
    }

 /*   @Override
    public List<AttrGroupVO> queryGroupWithAttrsByCid(Long catId) {

        // 根据分类查询分类下的所有组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));

        // 查询每个组下的所有规格参数
        return groupEntities.stream().map(attrGroupEntity -> this.queryGroupWithAttrs(attrGroupEntity.getAttrGroupId())).collect(Collectors.toList());
    }*/
}