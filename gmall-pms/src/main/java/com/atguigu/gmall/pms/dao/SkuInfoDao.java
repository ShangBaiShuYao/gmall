package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * sku信息
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-11-01 20:49:25
 */
@Mapper
@Repository
public interface SkuInfoDao extends BaseMapper<SkuInfoEntity> {
	
}
