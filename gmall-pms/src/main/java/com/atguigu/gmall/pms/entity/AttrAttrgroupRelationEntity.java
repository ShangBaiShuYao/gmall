package com.atguigu.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;

/**
 * 属性&属性分组关联
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:50:48
 */
@ApiModel
@Data
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * 属性id
	 */
	@ApiModelProperty(name = "attrId",value = "属性id")
	private Long attrId;
	/**
	 * 属性分组id
	 */
	@ApiModelProperty(name = "attrGroupId",value = "属性分组id")
	private Long attrGroupId;
	/**
	 * 属性组内排序
	 */
	@ApiModelProperty(name = "attrSort",value = "属性组内排序")
	private Integer attrSort;

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}


	public Long getAttrGroupId() {
		return attrGroupId;
	}

	public void setAttrGroupId(Long attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
}
