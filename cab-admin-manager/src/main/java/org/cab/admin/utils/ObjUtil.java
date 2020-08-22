package org.cab.admin.utils;

import io.netty.util.internal.ObjectUtil;
import org.cab.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjUtil.class);

    public static <T> T copyObj(Object obj, Class<T> tag)  {
        T t = null;
        try {
            Constructor<T> constructor = tag.getConstructor();
            t = constructor.newInstance();
            BeanUtils.copyProperties(obj, t);
        } catch (Exception e) {
            throw new CustomException("反射创建对象失败:"+e.getMessage());
        }
        return t;
    }
}
