package org.cab.user.config;

import org.cab.api.message.MessageParam;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqConfig {
    /**
     * 绑定 userQueue
     *
     * @return
     */
    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange()).with(MessageParam.User.BINDING_ROUTINGKEY)
                .noargs();
    }

    /**
     * 绑定retryQueue
     *重试队列 routingKey使用 queueName
     *
     * @return
     */
    @Bean
    public Binding retryQueueBinding() {
        return BindingBuilder.bind(retryQueue()).to(userExchange()).with(MessageParam.User.USER_RETRY_QUEUE)
                .noargs();
    }

    /**
     * 绑定fail  queue
     * 失败队列 routingKey使用 queueName
     *
     * @return
     */
    @Bean
    public Binding failQueueBinding() {
        return BindingBuilder.bind(failQueue()).to(userExchange()).with(MessageParam.User.USER_FAIL_QUEUE)
                .noargs();
    }

    /**
     * user exchange
     *
     * @return
     */
    @Bean
    public Exchange userExchange() {
        return new TopicExchange(MessageParam.User.USER_EXCHANGE, true, false);
    }

    /**
     * 实际消费queue
     *
     * @return
     */
    @Bean
    public Queue userQueue() {
        return new Queue(MessageParam.User.USER_CONSUMER_QUEUE, true);
    }


    /**
     * 消息消费异常 进入重试队列 等待重试
     * x-message-ttl 设置过期时间 过期消息进入  x-dead-letter-exchange
     * x-dead-letter-exchange 接收过期消息exchange
     * x-dead-letter-routing-key 路由 key
     *
     * @return
     *
     */
    @Bean
    public Queue retryQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", MessageParam.User.RETRY_TIME * 1000);
        arguments.put("x-dead-letter-exchange", MessageParam.User.USER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", MessageParam.User.BINDING_ROUTINGKEY);
        return new Queue(MessageParam.User.USER_RETRY_QUEUE, true, false, false, arguments);
    }


    /**
     * 失败queue
     * 消息处理异常 重试次数上限后进入此队列
     *
     * @return
     */
    @Bean
    public Queue failQueue() {
        return new Queue(MessageParam.User.USER_FAIL_QUEUE, true);
    }


}
