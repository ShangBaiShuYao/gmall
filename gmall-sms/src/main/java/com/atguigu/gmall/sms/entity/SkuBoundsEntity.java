package com.atguigu.gmall.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @Auther: 上白书妖
 * @Date: 2019/11/3 17:20
 * @Description:
 */
@ApiModel
@Data
@TableName("sms_sku_bounds")
public class SkuBoundsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty(name = "id",value = "id")
    private Long id;
    /**
     *
     */
    @ApiModelProperty(name = "skuId",value = "")
    private Long skuId;
    /**
     * 成长积分
     */
    @ApiModelProperty(name = "growBounds",value = "成长积分")
    private BigDecimal growBounds;
    /**
     * 购物积分
     */
    @ApiModelProperty(name = "buyBounds",value = "购物积分")
    private BigDecimal buyBounds;
    /**
     * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
     */
    @ApiModelProperty(name = "work",value = "优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]")
    private Integer work;


}