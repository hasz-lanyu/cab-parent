package org.cab.user.config;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class ShardingJdbcConfig {

    private static final Logger log = LoggerFactory.getLogger(ShardingJdbcConfig.class);

    @Bean
    public DataSource myDataSource() {
        DataSource dataSource = null;
        try {
            dataSource = MasterSlaveDataSourceFactory.createDataSource(ResourceUtils.getFile
                    ("classpath:sharding-jdbc.yml"));
        } catch (SQLException e) {
            log.error("数据源创建失败[{}]", e.getMessage());
        } catch (IOException e) {
            log.error("jdbc配置文件加载失败[{}]", e.getMessage());
        }
        return dataSource;
    }
}