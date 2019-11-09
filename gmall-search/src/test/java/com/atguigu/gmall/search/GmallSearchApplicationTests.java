package com.atguigu.gmall.search;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsClient;
import com.atguigu.gmall.search.vo.GoodsVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import com.atguigu.pmall.pms.entity.BrandEntity;
import com.atguigu.pmall.pms.entity.CategoryEntity;
import com.atguigu.pmall.pms.entity.SkuInfoEntity;
import com.atguigu.pmall.pms.entity.SpuInfoEntity;
import com.atguigu.pmall.pms.vo.SpuAttributeValueVO;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *  @作者  上白书妖
 *  @Date  2019/11/8 19:39
 *  @Email shangbaishuyao@163.com
 *  @Description
 */
@SpringBootTest
public class GmallSearchApplicationTests
{

    @Autowired
    private JestClient JestClient;

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private GmallWmsClient gmallWmsClient;

    @Test
    public void importData()
    {
        Long pageNum = 1l;
        Long pageSize = 100l;

        do {
            // 分页查询spu
            QueryCondition condition = new QueryCondition();
            condition.setPage(pageNum);
            condition.setLimit(pageSize);
//            Resp<List<SpuInfoEntity>> listResp = this.gmallPmsClient.querySpuPage(condition);
            Resp<List<SpuInfoEntity>> listResp = this.gmallPmsClient.querySpuPage(condition);
            // 获取当前页的spuInfo数据
            List<SpuInfoEntity> spuInfoEntities = listResp.getData();

            // 遍历spu获取spu下的所有sku导入到索引库中
            for (SpuInfoEntity spuInfoEntity : spuInfoEntities) {
                Resp<List<SkuInfoEntity>> skuResp = this.gmallPmsClient.querySkuBySpuId(spuInfoEntity.getId());
                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
                if (CollectionUtils.isEmpty(skuInfoEntities)){
                    continue;
                }
                skuInfoEntities.forEach(skuInfoEntity -> {
                    GoodsVO goodsVO = new GoodsVO();

                    // 设置sku相关数据
                    goodsVO.setName(skuInfoEntity.getSkuTitle());
                    goodsVO.setId(skuInfoEntity.getSkuId());
                    goodsVO.setPic(skuInfoEntity.getSkuDefaultImg());
                    goodsVO.setPrice(skuInfoEntity.getPrice());
                    goodsVO.setSale(100); // 销量
                    goodsVO.setSort(0); // 综合排序

                    // 设置品牌相关的
                    Resp<BrandEntity> brandEntityResp = this.gmallPmsClient.queryBrandById(skuInfoEntity.getBrandId());
                    BrandEntity brandEntity = brandEntityResp.getData();
                    if (brandEntity != null) {
                        goodsVO.setBrandId(skuInfoEntity.getBrandId());
                        goodsVO.setBrandName(brandEntity.getName());
                    }

                    // 设置分类相关的
                    Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
                    CategoryEntity categoryEntity = categoryEntityResp.getData();
                    if (categoryEntity != null) {
                        goodsVO.setProductCategoryId(skuInfoEntity.getCatalogId());
                        goodsVO.setProductCategoryName(categoryEntity.getName());
                    }

                    // 设置搜索属性
                    Resp<List<SpuAttributeValueVO>> searchAttrValueResp = this.gmallPmsClient.querySearchAttrValue(spuInfoEntity.getId());
                    List<SpuAttributeValueVO> spuAttributeValueVOList = searchAttrValueResp.getData();
                    goodsVO.setAttrValueList(spuAttributeValueVOList);

                    // 库存
                    Resp<List<WareSkuEntity>> resp = this.gmallWmsClient.queryWareBySkuId(skuInfoEntity.getSkuId());
                    List<WareSkuEntity> wareSkuEntities = resp.getData();
                    if (wareSkuEntities.stream().anyMatch(t -> t.getStock() > 0)) {
                        goodsVO.setStock(1l);
                    } else {
                        goodsVO.setStock(0l);
                    }

                    Index index = new Index.Builder(goodsVO).index("goods").type("info").id(skuInfoEntity.getSkuId().toString()).build();
                    try {
                        this.estClient.execute(index);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            pageSize = Long.valueOf(spuInfoEntities.size()); // 获取当前页的记录数
            pageNum++; // 下一页
        } while (pageSize == 100); // 循环条件

    }







    /*
     *  @作者  上白书妖
     *  @Date  2019/11/9 0:40
     *  @Email shangbaishuyao@163.com
     *  @Description  数据导入
     */
/*
    @Test
    public void importData()
    {
        //设置页面,每页显示数据
        Long pageNum = 1l;
        Long pageSize = 100l;
        
        do {
            //分页查询spu
            //分页查询已上架商品,即spu中publish_status=1的商品
            QueryCondition condition = new QueryCondition();
            condition.setPage(pageNum); //页码
            condition.setLimit(pageSize); //每页多少条
            Resp<List<SkuInfoEntity>> listResp = this.gmallPmsClient.querySpuPage(condition);
            //获取当前页的spuInfo数据
            List<SkuInfoEntity> spuInfoEntities = listResp.getData();//拿到当前页数据

            //拿到spuInfoEntities之后就去遍历他,遍历spu获取spu下的所有sku导入到索引库中
            for (SkuInfoEntity spuInfoEntity : spuInfoEntities) {
                Resp<List<SkuInfoEntity>> skuResp = this.gmallPmsClient.querySkuBySpuId(spuInfoEntity.getId());
                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
                if (CollectionUtils.isEmpty(skuInfoEntities)){
                    continue;
                }
                 skuInfoEntities.forEach(skuInfoEntity ->
                 {
                     GoodsVO goodsVO = new GoodsVO();

                     //设置sku相关数据
                     goodsVO.setName(skuInfoEntity.getSkuTitle());
                     goodsVO.setId(skuInfoEntity.getSkuId());
                     goodsVO.setPic(skuInfoEntity.getSkuDefaultImg());
                     goodsVO.setPrice(skuInfoEntity.getPrice());
                     goodsVO.setSale(100); //销量
                     goodsVO.setSort(0); //综合排序


                     //设置品牌相关的
                     Resp<BrandEntity> brandEntityResp = this.gmallPmsClient.queryBrandById(skuInfoEntity.getBrandId());
                     BrandEntity brandEntity = brandEntityResp.getData();
                     if (brandEntity !=null) {
                         goodsVO.setBrandId(skuInfoEntity.getBrandId());
                         goodsVO.setBrandName(brandEntity.getName());
                     }

                     //设置分类相关的
                     Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
                     CategoryEntity categoryEntity = categoryEntityResp.getData();
                     if (categoryEntity != null) {

                         goodsVO.setProductCategoryId(skuInfoEntity.getCatalogId());
                         goodsVO.setProductCategoryName(categoryEntity.getName());
                     }

                     //设置搜索属性
                     Resp<List<SpuAttributeValueVO>> searchAttrValueResp = this.gmallPmsClient.querySearchAttrValue(spuInfoEntity.getId());
                     List<SpuAttributeValueVO> spuAttributeValueVOList = searchAttrValueResp.getData();
                     goodsVO.setAttrValueList(spuAttributeValueVOList);

                     //库存
                     Resp<List<WareSkuEntity>> resp = this.gmallWmsClient.queryWareSkuBySkuId(skuInfoEntity.getSkuId());
                     List<WareSkuEntity> wareSkuEntity = resp.getData();
                     if (wareSkuEntity.stream().anyMatch(t -> t.getStock() > 0)) {
                         goodsVO.setStock(1l);
                     } else {
                         goodsVO.setStock(0l);
                     }
                     goodsVO.setSort(null);

                     Index build = new Index.Builder(goodsVO).index("goods").type("info").id(skuInfoEntity.getSkuId().toString()).build();
                 });
             }

            pageSize = Long.valueOf(spuInfoEntities.size()); //获取当前页的记录数
            pageNum++; //下一页

        }while (pageSize == 100); //如果pageSize==100就一直去查,循环条件
    }
*/










    
    /*
     * @Description 新增
     * @Date   2019/11/8 19:22
     */
    @Test
    public void create() throws IOException
    {
        User user = new User("书妖", 22, "password");
        Index index = new Index.Builder(user).index("user").type("info").id("520").build();
        JestClient.execute(index);
    }



    /*
     * @Description  更新
     * @Date   2019/11/8 19:22
     */
    @Test
    public void update() throws IOException
    {

        User user = new User("杨晨", 24, "000");
        Map<String, User> map = new HashMap<>();
        map.put("doc", user);

        Update action = new Update.Builder(map).index("user").type("info").id("520").build();

        DocumentResult result = JestClient.execute(action);
        System.out.println(result.toString());
    }



    /*
     * @Description 查询
     * @Date   2019/11/8 19:31
     */
    @Test
    public void search() throws IOException
    {
        // 查询表达式
        String query="{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";

        Search action = new Search.Builder(query).addIndex("user").addType("info").build();

        SearchResult result = JestClient.execute(action);
        System.out.println(result.toString());
        // 两种获取查询结果集的方式
        System.out.println(result.getSourceAsObjectList(User.class, false));
        result.getHits(User.class).forEach(hit -> {
            System.out.println(hit.source);
        });
    }




    /*
     * @Description  查询一个
     * @Date   2019/11/8 19:35
     */
    @Test
    public void test2() throws IOException
    {

        Get action = new Get.Builder("user", "1").build();
        DocumentResult result = this.JestClient.execute(action);
        System.out.println(result.getSourceAsObject(User.class).toString());
    }



    /*
     * @Description 删除
     * @Date   2019/11/8 19:37
     */
    @Test
    public void delete() throws IOException {

        Delete action = new Delete.Builder("1").index("user").type("info").build();

        DocumentResult result = JestClient.execute(action);
        System.out.println(result.toString());
    }





}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User
{
    private String name;
    private Integer age;
    private String password;
}