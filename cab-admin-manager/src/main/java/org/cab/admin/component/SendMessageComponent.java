package org.cab.admin.component;

import org.apache.commons.lang3.StringUtils;

import org.cab.api.message.MessageParam;
import org.cab.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class SendMessageComponent {
    private static final Logger log = LoggerFactory.getLogger(SendMessageComponent.class);

    @Autowired
    SnowFlakeComponent snowFlakeComponent;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * mq消息发送
     *
     * @param exchangeName
     * @param routingKey
     * @param message
     */
    public void send(String exchangeName, String routingKey, Object message, @Nullable String correlationDataId) {
        if (StringUtils.isBlank(exchangeName) || StringUtils.isBlank(routingKey) || message == null) {
            return;
        }

        Message finalMessage = convertMessage(exchangeName, routingKey, message);
        CorrelationData correlationData = getCorrelationData(finalMessage, correlationDataId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, finalMessage, correlationData);
    }

    public void send(String exchangeName, String routingKey, Object message) {
        send(exchangeName, routingKey, message, null);
    }

    /**
     * 构造 消息回调 correlationData
     *
     * @param message           消息体
     * @param correlationDataId 回调唯一id
     * @return
     */
    private CorrelationData getCorrelationData(Message message, @Nullable String correlationDataId) {
        //传递过来的Id为空 自动生成一个
        String finalCorrelationDataId = correlationDataId == null ? snowFlakeComponent.getId().toString()
                : correlationDataId;
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(finalCorrelationDataId);
        correlationData.setReturnedMessage(message);
        return correlationData;

    }

    /**
     * 设置消息json格式、messageId作为消息唯一id
     * send_exchange send_routingKey 消息头记录一下消息发送的exchange以及routingKey 与配置无关
     *
     * @param exchange   消息投递的exchange
     * @param routingKey 消息路由路径
     * @param message    要发送的内容
     * @return
     */
    private Message convertMessage(String exchange, String routingKey, Object message) {
        return MessageBuilder.withBody(JsonUtil.toJson(message).getBytes())
                .setContentType(MessageParam.CONTENT_TYPE)
                .setMessageId(snowFlakeComponent.getId().toString())
                .setHeader("send_exchange", exchange)
                .setHeader("send_routingKey", routingKey)
                .build();
    }

}
