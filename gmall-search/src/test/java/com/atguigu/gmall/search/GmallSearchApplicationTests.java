package com.atguigu.gmall.search;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsClient;
import com.atguigu.gmall.search.vo.GoodsVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class GmallSearchApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private GmallWmsClient gmallWmsClient;

    @Test
    public void importData(){

        Long pageNum = 1l;
        Long pageSize = 100l;

        do {
            // 分页查询spu
            QueryCondition condition = new QueryCondition();
            condition.setPage(pageNum);
            condition.setLimit(pageSize);
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

                    /*
                     * @Description 设置sku相关数据
                     * @Date   2019/11/15 21:25
                     */
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
                        this.jestClient.execute(index);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            pageSize = Long.valueOf(spuInfoEntities.size()); // 获取当前页的记录数
            pageNum++; // 下一页
        } while (pageSize == 100); // 循环条件

    }










    @Test
    void contextLoads() throws IOException {

        Delete build = new Delete.Builder("1").index("user").type("info").build();
        jestClient.execute(build);

//        Index index = new Index.Builder(new User("liuyan", "123456", 18)).index("user").type("info").id("1").build();
//
//        jestClient.execute(index);

//        Map<String, Object> map = new HashMap<>();
//        map.put("doc", new User("bingbing", null, null));
//
//        Update update = new Update.Builder(map).index("user").type("info").id("1").build();
//        DocumentResult result = jestClient.execute(update);
//        System.out.println(result.toString());

//        Get get = new Get.Builder("user", "1").build();
//
//        System.out.println(jestClient.execute(get));

//        String query = "{\n" +
//                "  \"query\": {\n" +
//                "    \"match_all\": {}\n" +
//                "  }\n" +
//                "}";
//        Search search = new Search.Builder(query).addIndex("user").addType("info").build();
//        SearchResult searchResult = jestClient.execute(search);
//        System.out.println(searchResult.getSourceAsObject(User.class, false));
//        List<SearchResult.Hit<User, Void>> hits = searchResult.getHits(User.class);
//        hits.forEach(hit -> {
//            System.out.println(hit.source);
//        });

    }



}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private String name;
    private String password;
    private Integer age;
}
/*
package com.atguigu.gmall.search;

import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsClient;
import com.atguigu.gmall.search.vo.GoodsVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;


*/
/*
 *  @作者  上白书妖
 *  @Date  2019/11/8 19:39
 *  @Email shangbaishuyao@163.com
 *//*

@SpringBootTest(classes = GmallSearchApplication.class)
public class GmallSearchApplicationTests
{

    @Autowired
    private JestClient jestClient;

    @Autowired
    private GmallPmsClient gmallPmsClient; //注入远程调用

    @Autowired
    private GmallWmsClient gmallWmsClient; //注入远程调用


    @Test
    public void importData()
    {
        Long pageNum = 1l; //查询第一页
        Long pageSize = 100l; //每页查询一百条

        */
/*
            do{ }while (pageSize == 100) 如果每页是一百条就一直去查
        *//*


        do {
            // 分页查询spu  condition就是一个分页的对象,万事万物就对象,里面可以设置分页的一些条件,页码,等
            QueryCondition condition = new QueryCondition();
            condition.setPage(pageNum);
            condition.setLimit(pageSize);

            */
/*
             *远程调用,查询分页,需要有分页的对象,里面需要用认为设置set每页该分为多少条记录等
             *//*

            Resp<List<SpuInfoEntity>> listResp = this.gmallPmsClient.querySpuPage(condition);


            //获取当前页spuinfo数据
            List<SpuInfoEntity> spuInfoEntities = listResp.getData();
            // 遍历spu获取spu下的所有sku导入到索引库中
            for (SpuInfoEntity spuInfoEntity : spuInfoEntities) {
                // 远程调用 根据spuId查询sku(querySkuBySpuId) 分页查询 (每条记录都是一个实体类对象,所以叫面向对象,根据对象查id)
                Resp<List<SkuInfoEntity>> skuResp = this.gmallPmsClient.querySkuBySpuId(spuInfoEntity.getId());
                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
                if (CollectionUtils.isEmpty(skuInfoEntities))
                {
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
                        this.jestClient.execute(index);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            pageSize = Long.valueOf(spuInfoEntities.size()); // 获取当前页的记录数
            pageNum++; // 下一页
        } while (pageSize == 100); // 循环条件

    }







    */
/*
     *  @作者  上白书妖
     *  @Date  2019/11/9 0:40
     *  @Email shangbaishuyao@163.com
     *  @Description  数据导入
     *//*

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
*//*











    
    */
/*
     * @Description 新增
     * @Date   2019/11/8 19:22
     *//*

    @Test
    public void create() throws IOException
    {
        User user = new User("书妖", 22, "password");
        Index index = new Index.Builder(user).index("user").type("info").id("520").build();
        jestClient.execute(index);
    }



    */
/*
     * @Description  更新
     * @Date   2019/11/8 19:22
     *//*

//    @Test
//    public void update() throws IOException
//    {
//
//        User user = new User("杨晨", 24, "000");
//        Map<String, User> map = new HashMap<>();
//        map.put("doc", user);
//
//        Update action = new Update.Builder(map).index("user").type("info").id("520").build();
//
//        DocumentResult result = jestClient.execute(action);
//        System.out.println(result.toString());
//    }



    */
/*
     * @Description 查询
     * @Date   2019/11/8 19:31
     *//*

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

        SearchResult result = jestClient.execute(action);
        System.out.println(result.toString());
        // 两种获取查询结果集的方式
        System.out.println(result.getSourceAsObjectList(User.class, false));
        result.getHits(User.class).forEach(hit -> {
            System.out.println(hit.source);
        });
    }




    */
/*
     * @Description  查询一个
     * @Date   2019/11/8 19:35
     *//*

    @Test
    public void test2() throws IOException
    {

        Get action = new Get.Builder("user", "1").build();
        DocumentResult result = this.jestClient.execute(action);
        System.out.println(result.getSourceAsObject(User.class).toString());
    }



    */
/*
     * @Description 删除
     * @Date   2019/11/8 19:37
     *//*

    @Test
    public void delete() throws IOException {

        Delete action = new Delete.Builder("1").index("user").type("info").build();

        DocumentResult result = jestClient.execute(action);
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
}*/
