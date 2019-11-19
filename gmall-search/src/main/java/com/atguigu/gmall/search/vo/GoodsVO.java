package com.atguigu.gmall.search.vo;

import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/*
 * @Auther: 上白书妖
 * @Date: 2019/11/8 21:02
 * @Description:
 */
@Data
public class GoodsVO
{
    /*
     * @Description  分页查询spu已经有了,将来我们只要远程调用即可
     * 然后遍历spu,在然后就像库存中去导入,不能直接到,因为我们要导入的事sku,所以我们要根据spu来查询sku,这一步是要feignclient远程调用的
     * 所以得有这样的一个接口,找pms,看里面有没有根据spu的id查询sku的一个controller方法呢?结果是有的
     * 有了之后,将来是要把每一个sku变成goodsvo的,向索引库去新增
     * 你要把sku变成goodsvo你还需要那些数据呢?这些数据是否也要远程调用呢?
     * @Date   2019/11/15 19:15
     */
    private Long id;  //skuId
    private Long brandId; //品牌id
    private String brandName;  //品牌名
    private Long productCategoryId;  //sku的分类id
    private String productCategoryName; //sku的名字


    private String pic; //sku的默认图片
    private String name;//这是需要检索的sku的标题
    private BigDecimal price;//sku-price；
    private Integer sale;//sku-sale 销量
    /*
     * @Description 库存是无法根据spu_id判断是否是检索属性的,必须两张表关联查询,但是呢,我们没有写这个controller方法
     * 需要自己去写,去两表查询的去完成
     * @Date   2019/11/15 19:29
     */
    private Long stock;//sku-stock 库存
    private Integer sort;//排序分 热度分

    //保存当前sku所有需要检索的属性；
    //检索属性来源于spu的基本属性中的search_type=1（销售属性都已经拼接在标题中了）
    /*
     * @Description 销售属性不需要检索的,可检索数据都是基本属性,因为京东上的销售属性都放在了标题上了
     * 可检索属性都是一个集合,单独写一个类
     * @Date   2019/11/15 18:51
     */
    private List<SpuAttributeValueVO> attrValueList;//检索属性
}