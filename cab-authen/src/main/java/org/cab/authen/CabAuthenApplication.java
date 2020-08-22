package org.cab.authen;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableDubbo
@MapperScan("org.cab.authen.mapper")

public class CabAuthenApplication {
    public static void main(String[] args) {
        SpringApplication.run(CabAuthenApplication.class,args);
    }
}
