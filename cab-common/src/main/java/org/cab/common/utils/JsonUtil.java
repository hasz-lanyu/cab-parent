package org.cab.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cab.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper json = new ObjectMapper();

    public static String toJson(Object obj){
        try {
            return json.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("[{}]转json失败：{}",obj,e.getMessage());
        }
        throw  new CustomException("序列化失败");
    }

    public static <T> T toObj(String str ,Class<T> clazz){
        try {
         return json.readValue(str, clazz);
        } catch (IOException e) {
            log.error("[{}]解析json失败：{}",str,e.getMessage());
        }
        throw  new CustomException("解析json失败");
    }

}
