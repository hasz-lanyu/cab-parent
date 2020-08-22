package org.cab.admin;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDubbo
@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class})
@EnableRabbit
@MapperScan("org.cab.admin.mapper")

public class CabAdminManagerApplication {
    public static void main(String[] args) {

        SpringApplication.run(CabAdminManagerApplication.class,args);
    }
}
