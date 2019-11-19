package com.atguigu.gmall.search.listener;


import com.atguigu.gmall.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: 上白书妖
 * @Date: 2019/11/16 00:36
 * @Description:
 */
/*
@Component
public class SpuInfoListener {

    @Autowired
    private SearchService searchService;

    */
/**
     * 处理insert的消息
     *
     * @param id
     * @throws Exception
     *//*

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "gmall.item.create.queue", durable = "true"),
            exchange = @Exchange(
                    value = "gmall.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert"}))
    public void listenCreate(Long id) throws Exception {
        if (id == null) {
            return;
        }
        // 创建索引
        this.searchService.createIndex(id);
    }
}*/
