package com.atguigu.gmall.search;

import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/*
 *  @作者  上白书妖
 *  @Date  2019/11/8 19:39
 *  @Email shangbaishuyao@163.com
 *  @Description
 */
@SpringBootTest
class GmallSearchApplicationTests
{

    @Autowired
    private JestClient JestClient;

    
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