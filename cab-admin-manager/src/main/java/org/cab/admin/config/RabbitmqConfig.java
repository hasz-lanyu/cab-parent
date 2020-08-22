package org.cab.admin.config;

import org.cab.api.message.MessageParam;
import org.cab.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 消息队列 模板配置
 */
@Configuration
public class RabbitmqConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * rabbitTemplate 配置添加
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //消息 to exchange回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                //消息发送到exchange失败
                Message returnedMessage = correlationData.getReturnedMessage();
                String id = correlationData.getId();
                //消息保存到缓存
                stringRedisTemplate.opsForHash().put(MessageParam.CACHEKEY_MESSAGE_SEND_ERR, id,
                        JsonUtil.toJson(returnedMessage));
            }
        });
        //防止exchange找不到queue 不回调 消息丢弃
        rabbitTemplate.setMandatory(true);
        //消息从 exchange to queue 失败回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息路由到队列失败：message[{}],replyCode[{}],replyText[{}],exchange[{}],routingKey[{}]",
                    message, replyCode, replyText, exchange, routingKey);
            //消息保存到缓存
            stringRedisTemplate.opsForHash().put(MessageParam.CACHEKEY_MESSAGE_SEND_ERR,message
                    .getMessageProperties().getMessageId(), JsonUtil.toJson(message));
        });
        return rabbitTemplate;
    }

    /**
     * 消息转换器   json
     *
     * @return
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
