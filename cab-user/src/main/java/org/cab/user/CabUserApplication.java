package org.cab.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("org.cab.user.mapper")
@EnableRabbit
public class CabUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CabUserApplication.class, args);
    }
}
