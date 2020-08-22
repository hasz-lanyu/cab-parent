package org.cab.admin.component;

import org.cab.common.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeComponent {
    @Value("${snowflake.machineid}")
    private long machineid;
    @Value("${snowflake.datacenterid}")
    private long datacenterid;

    private static volatile SnowFlake instance;


    public Long getId() {
        return snowFlake().nextId();
    }

    private SnowFlake snowFlake() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new SnowFlake(datacenterid, machineid);
                }
            }
        }
        return instance;
    }
}
