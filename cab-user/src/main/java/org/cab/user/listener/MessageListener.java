package org.cab.user.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;
import org.cab.api.message.MessageParam;
import org.cab.api.user.service.UserOperationService;
import org.cab.common.exception.CustomException;
import org.cab.common.utils.NumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class MessageListener {
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    private UserOperationService userOperationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "user@consumer")
    public void userEventListener(Message message, Channel channel) throws IOException {
        String cacheKey = MessageParam.User.CACHEKEY_MESSAGE_ACTIVE + message.getMessageProperties().getMessageId();
        if (stringRedisTemplate.hasKey(cacheKey)) {
            //key存在 代表消息已处理
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            //未处理的消息
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            String sourceRoutingKey = (String) headers.get("send_routingKey");
            try {
                messageHandle(sourceRoutingKey, new String(message.getBody()));
                //消息成功处理 缓存添加标识
                stringRedisTemplate.opsForValue().set(cacheKey, message.getMessageProperties().getMessageId(),
                        NumUtil.randomMill(30), TimeUnit.MILLISECONDS);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                //消费出现异常
                Object retryCount = headers.get("retryCount");
                if (retryCount == null || Integer.parseInt(retryCount.toString()) < 3) {
                    //如果首次出现异常或者retryCount小于3次 转发到重试队列
                    channel.basicPublish(MessageParam.User.USER_EXCHANGE, MessageParam.User.USER_FAIL_QUEUE,
                            true, this.properties(message), message.getBody());
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    //retryCount大于3  进入失败队列
                    channel.basicPublish(MessageParam.User.USER_EXCHANGE, MessageParam.User.USER_FAIL_QUEUE,
                            true, this.properties(message), message.getBody());
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
            }
        }

    }

    /**
     * retryCount 重试次数
     *
     * @param message
     * @return messageProperties
     */
    private AMQP.BasicProperties properties(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Integer retryCount = headers.get("retryCount") == null ?
                1 : (Integer.parseInt(headers.get("retryCount").toString()) + 1);
        headers.put("retryCount", retryCount);

        return MessageProperties.PERSISTENT_TEXT_PLAIN.builder()
                .contentType(MessageParam.CONTENT_TYPE)
                .headers(headers)
                .correlationId(message.getMessageProperties().getCorrelationId())
                .build();
    }

    /**
     * 根据sourceRoutingKey进行业务判断 如需扩展加 else if 进行判断
     *
     * @param sourceRoutingKey 传递过来的routingKey
     * @param param            对象的json
     */
    private void messageHandle(String sourceRoutingKey, String param) {
        if (MessageParam.User.UPDATELOGINTIME_ROUTINGKEY.equals(sourceRoutingKey)) {
            userOperationService.updateLoginTimeById(Long.parseLong(param));
        } else {
            throw new CustomException("消息处理失败");
        }
        if (log.isDebugEnabled()) {
            log.debug("消息处理成功routingKey:[{}],param:[{}]", sourceRoutingKey, param);
        }
    }

}
