package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.CategoryVO;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  @作者  上白书妖
 *  @Date  2019/11/15 20:00
 *  @Email shangbaishuyao@163.com
 *  @Description  这里的方法就是为了获取goodsVO的,整个goodsVo类放在了下面,自己去对对
 *
 */
public interface  GmallPmsApi {


    // 根据id查询sku，对应SkuInfoController的info方法
    @GetMapping("pms/skuinfo/info/{skuId}")
    public Resp<SkuInfoEntity> querySkuById(@PathVariable("skuId") Long skuId);

    @GetMapping("pms/skuimages/{skuId}") // 根据skuId查询sku的图片
    public Resp<List<SkuImagesEntity>> queryImagesBySkuId(@PathVariable("skuId") Long skuId);

    @GetMapping("pms/skusaleattrvalue/{spuId}")  // 根据spuId查询所有的销售属性
    public Resp<List<SkuSaleAttrValueEntity>> querySkuSaleAttrValueBySpuId(@PathVariable("spuId")Long spuId);


    /**
     * 商品分类  查询以及分类把里面的参数level参数写成1就是一级分类呀   parentId 根据父Id查询子分类把它设置成0不就可以了吗
     * @param level
     * @param parentCid
     * @return
     */
    @GetMapping("pms/category")
    public Resp<List<CategoryEntity>> queryCategory(@RequestParam(value="level", defaultValue = "0")
                                                            Integer level  , @RequestParam(value="parentCid", required = false)Long parentCid );



    @GetMapping("pms/skusaleattrvalue/sku/{skuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrBySkuId(@PathVariable("skuId")Long skuId);

    @GetMapping("pms/spuinfo/info/{id}")
    public Resp<SpuInfoEntity> querySpuById(@PathVariable("id") Long id);

    @GetMapping("pms/skuimages/{skuId}")
    public Resp<List<String>> queryPicsBySkuId(@PathVariable("skuId")Long skuId);

    @GetMapping("pms/skusaleattrvalue/{spuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrValues(@PathVariable("spuId")Long spuId);

    @GetMapping("pms/spuinfodesc/info/{spuId}")
    public Resp<SpuInfoDescEntity> querySpuDescById(@PathVariable("spuId") Long spuId);

    @GetMapping("pms/attrgroup/item/group/{cid}/{spuId}")
    public Resp<List<GroupVO>> queryGroupVOByCid(@PathVariable("cid")Long cid, @PathVariable("spuId")Long spuId);

    @PostMapping("pms/spuinfo/list")
    public Resp<List<SpuInfoEntity>> querySpuPage(@RequestBody QueryCondition queryCondition);

    @GetMapping("pms/skuinfo/{spuId}")
    public Resp<List<SkuInfoEntity>> querySkuBySpuId(@PathVariable("spuId")Long spuId);

    @GetMapping("pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    @GetMapping("pms/category/info/{catId}")
    public Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    @GetMapping("pms/category")
    public Resp<List<CategoryEntity>> queryCategories(@RequestParam(value = "level", defaultValue = "0")Integer level, @RequestParam(value = "parentCid", required = false)Long parentCid);

    @GetMapping("pms/category/{pid}")
    public Resp<List<CategoryVO>> queryCategoryWithSub(@PathVariable("pid")Long pid);

    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId")Long spuId);
}

/*
@Data
public class GoodsVO
{
    */
/*
     * @Description  分页查询spu已经有了,将来我们只要远程调用即可
     * 然后遍历spu,在然后就像库存中去导入,不能直接到,因为我们要导入的事sku,所以我们要根据spu来查询sku,这一步是要feignclient远程调用的
     * 所以得有这样的一个接口,找pms,看里面有没有根据spu的id查询sku的一个controller方法呢?结果是有的
     * 有了之后,将来是要把每一个sku变成goodsvo的,向索引库去新增
     * 你要把sku变成goodsvo你还需要那些数据呢?这些数据是否也要远程调用呢?
     * @Date   2019/11/15 19:15
     *//*

    private Long id;  //skuId
    private Long brandId; //品牌id
    private String brandName;  //品牌名
    private Long productCategoryId;  //sku的分类id
    private String productCategoryName; //sku的名字


    private String pic; //sku的默认图片
    private String name;//这是需要检索的sku的标题
    private BigDecimal price;//sku-price；
    private Integer sale;//sku-sale 销量
    */
/*
     * @Description 库存是无法根据spu_id判断是否是检索属性的,必须两张表关联查询,但是呢,我们没有写这个controller方法
     * 需要自己去写,去两表查询的去完成
     * @Date   2019/11/15 19:29
     *//*

    private Long stock;//sku-stock 库存
    private Integer sort;//排序分 热度分

    //保存当前sku所有需要检索的属性；
    //检索属性来源于spu的基本属性中的search_type=1（销售属性都已经拼接在标题中了）
    */
/*
     * @Description 销售属性不需要检索的,可检索数据都是基本属性,因为京东上的销售属性都放在了标题上了
     * 可检索属性都是一个集合,单独写一个类
     * @Date   2019/11/15 18:51
     *//*

    private List<SpuAttributeValueVO> attrValueList;//检索属性
}
*/






















/*
package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/*
 * @Auther: 上白书妖
 * @Date: 2019/11/9 00:03
 * @Description: 实体类对象
 *//*

public interface GmallPmsApi
{


    */
/*
     * @Description 根据skuId查询sku
     * @Date   2019/11/9 0:11
     *//*

    @PostMapping("pms/spuinfo/list")
    public Resp<List<SpuInfoEntity>> querySpuPage(@RequestBody QueryCondition queryCondition);



        */
/*
     * @Description 根据sku查询spu的ID
     * @Date   2019/11/9 0:12
     *//*

    @GetMapping("pms/skuinfo/{spuId}")
    public Resp<List<SkuInfoEntity>> querySkuBySpuId(@PathVariable("spuId")Long spuId);


    */
/*
     * @Description  根据品牌id查询品牌
     * @Date   2019/11/9 0:17
     *//*

   */
/* @GetMapping("pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);*//*

    @GetMapping("pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    */
/*
     * @Description 根据categoryId查询分类
     * @Date   2019/11/9 0:19
     *//*

    @GetMapping("pms/category/info/{catId}")
    public Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    */
/*
     * @Description 根据spuId查询检索属性
     * @Date   2019/11/9 0:24
     *//*

    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId")Long spuId);

}*/
