package org.cab.common.utils;

import java.util.Random;

public class NumUtil {
    private static final Random random = new Random();

    /**
     * @param minuteBound 随机 1 -bound 单位分钟
     * @return 随机的分钟 毫秒
     */
    public static Long randomMill(Integer minuteBound) {
        Integer start = 60000;
        Integer end = minuteBound * start;
        Integer result = random.nextInt(end - start) + start;
        return result.longValue();
    }
}
