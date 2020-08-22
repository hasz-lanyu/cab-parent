package org.cab.api.message;

import java.io.Serializable;

public class MessageInfo implements Serializable {
    private static final long serialVersionUID = -1730874624730416145L;
    private String id;
    /**
     * 0未处理 1处理成功
     */
    private Integer status;
    /**
     * 处理次数
     */
    private Integer count;
    private String routingKey;
    private String exchangeName;
    private Object message;

    public MessageInfo() {
    }

    public MessageInfo(String id, Integer status, Integer count, String routingKey, String exchangeName, Object message) {
        this.id = id;
        this.status = status;
        this.count = count;
        this.routingKey = routingKey;
        this.exchangeName = exchangeName;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

}

