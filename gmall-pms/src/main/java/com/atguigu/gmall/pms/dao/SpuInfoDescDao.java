package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * spu信息介绍
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:47
 */
@Mapper
@Repository
public interface SpuInfoDescDao extends BaseMapper<SpuInfoDescEntity> {
	
}
