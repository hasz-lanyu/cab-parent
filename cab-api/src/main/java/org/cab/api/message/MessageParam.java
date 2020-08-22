package org.cab.api.message;


public class MessageParam {
    public static final String CONTENT_TYPE = "application/json";
    public static final String CACHEKEY_MESSAGE_SEND_ERR = "message:send:err";

    public static final class User {
        public static final String CACHEKEY_MESSAGE_ACTIVE = "message:info:active";
        public static final String USER_EXCHANGE = "user@exchange";
        public static final String USER_CONSUMER_QUEUE = "user@consumer";
        public static final String USER_RETRY_QUEUE = "user@retry";
        public static final String USER_FAIL_QUEUE = "user@fail";
        public static final String BINDING_ROUTINGKEY = "user.consumer.#";
        public static final String UPDATELOGINTIME_ROUTINGKEY = "user.consumer.updateLoginTime";

        //消息重试间隔时间 单位秒
        public static final Integer RETRY_TIME = 30;


    }


}
