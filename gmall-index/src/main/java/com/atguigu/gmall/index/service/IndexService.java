package com.atguigu.gmall.index.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.feign.GmallPmsClient;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategoryVO;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @Auther: 上白书妖
 * @Date: 2019/11/17 00:06
 * @Description:
 */
@Service
public class IndexService
{
    @Autowired
    private GmallPmsClient gmallPmsClient;

    /*
     * @Description 为什么使用StringRedisTemplate而不直接使用redisTemplate呢?
     * 因为redisTemplate会出现乱码问题呀,redis最终保存的事字符串,不管你是hash还是json等
     * @Date   2019/11/17 14:39
     */
    @Autowired
    private StringRedisTemplate redisTemplate;


    /*
     * @Description 1.引入了jedis依赖,2.添加配置,不是传统的配置,而是使用java代码配置详情情况看config报下的Java代码配置 ,java代码
     * 配置的都要加上一个@Configuration的一个配置注解
     * @Date   2019/11/17 16:54
     */
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedissonClient redissonClient;

    private static final String KEY_PREFIX ="index:category :";

    /*
     * @Description
     * @Date   2019/11/17 12:46
     */
    public List<CategoryEntity> queryLevelCategory()
    {
        //测试redis是否可可以存储数据
        this.redisTemplate.opsForValue().set("key","value");
        System.out.println("=====================" + this.redisTemplate.opsForValue().get("key"));


        Resp<List<CategoryEntity>> resp = this.gmallPmsClient.queryCategories(1, null);
        return resp.getData();
    }

    /*
     * @Description 直接读取redis缓存中的东西,不直接访问mysql
     * @Date   2019/11/17 12:46
     */
    public List<CategoryVO> queryCategoryVO(Long pid)
    {

//        Resp<List<CategoryVO>> listResp = this.gmallPmsClient.queryCategoryWithSub(pid);
//        return listResp.getData();


        //1.查询缓存,如果缓存中有的话直接返回
        String cache = this.redisTemplate.opsForValue().get(KEY_PREFIX + pid);
        if(StringUtils.isNotBlank(cache))
        {
            return JSON.parseArray(cache, CategoryVO.class);
        }

        //2.如果缓存中没有的话,直接查询数据库
        Resp<List<CategoryVO>> listResp = this.gmallPmsClient.queryCategoryWithSub(pid);
        List<CategoryVO> categoryVOS = listResp.getData();

        //3.查询完之后放入缓存
        this.redisTemplate.opsForValue().set(KEY_PREFIX+pid,JSON.toJSONString(categoryVOS));

        return categoryVOS;
    }



    /*
     * @Description 测试分布式锁
     * @Date   2019/11/17 16:00
     */

    public String testLock() {

        RLock lock = this.redissonClient.getLock("lock"); // 只要锁的名称相同就是同一把锁
        lock.lock(); // 加锁

        // 查询redis中的num值
        String value = this.redisTemplate.opsForValue().get("num");
        // 没有该值return
        if (StringUtils.isBlank(value)) {
            return value;
        }
        // 有值就转成成int
        int num = Integer.parseInt(value);
        // 把redis中的num值+1
        this.redisTemplate.opsForValue().set("num", String.valueOf(++num));

        lock.unlock(); // 解锁
        return value;
    }

   /* public String testLock() {
        //所有请求,竞争锁
        Boolean lock = this.redisTemplate.opsForValue().setIfAbsent("lock", "上白书妖要刷牙!");


        //获取到锁执行业务逻辑
        if (lock) {
            String numString = this.redisTemplate.opsForValue().get("num");
            if (StringUtils.isBlank(numString)) {
                return null;
            }
            int num = Integer.parseInt(numString);
            this.redisTemplate.opsForValue().set("num", String.valueOf(++num));

            //释放锁
            this.redisTemplate.delete("lock");
        } else {
            //没有获取到锁的请求进行重试
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
            return "已经增加成功!";
    }*/




    public String testLock1() {
        // 所有请求，竞争锁
        String uuid = UUID.randomUUID().toString();
        //设置时间 重试 ,重试我们有专业的重试框架spring-retry来做重试,这儿我们没时间讲而已
        Boolean lock = this.redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        // 获取到锁执行业务逻辑
        if (lock) {
            String numString = this.redisTemplate.opsForValue().get("num");
            if (StringUtils.isBlank(numString)) {
                return null;
            }
            int num = Integer.parseInt(numString);
            this.redisTemplate.opsForValue().set("num", String.valueOf(++num));

            // 释放锁
            Jedis jedis = null;
            try {
                jedis = this.jedisPool.getResource();
                /*
                 * @Description 优化之LUA脚本保证删除的原子性 添加完配置等等后使用LUA脚本
                 * @Date   2019/11/17 16:28
                 */
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Arrays.asList("lock"), Arrays.asList(uuid));
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
           /*
            * @Description 因为redisTemplate无法使用,时灵时不灵的,所以我们使用了LUA脚本
            * @Date   2019/11/17 17:02
            */
//            this.redisTemplate.execute(new DefaultRedisScript<>(script), Arrays.asList("lock"), uuid);
//            if (StringUtils.equals(uuid, this.redisTemplate.opsForValue().get("lock"))){
//                this.redisTemplate.delete("lock");
//            }
        } else {
            // 没有获取到锁的请求进行重试
            try {
                TimeUnit.SECONDS.sleep(1);
                testLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "已经增加成功";
    }

    public String testRead() {
        RReadWriteLock readWriteLock = this.redissonClient.getReadWriteLock("readWriteLock");
        readWriteLock.readLock().lock(10l, TimeUnit.SECONDS);

        String msg = this.redisTemplate.opsForValue().get("msg");

//        readWriteLock.readLock().unlock();
        return msg;
    }

    public String testWrite() {
        RReadWriteLock readWriteLock = this.redissonClient.getReadWriteLock("readWriteLock");
        readWriteLock.writeLock().lock(10l, TimeUnit.SECONDS);

        String msg = UUID.randomUUID().toString();
        this.redisTemplate.opsForValue().set("msg", msg);

//        readWriteLock.writeLock().unlock();
        return "数据写入成功。。 " + msg;
    }
}