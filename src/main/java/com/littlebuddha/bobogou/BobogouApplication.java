package com.littlebuddha.bobogou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.littlebuddha.bobogou.modules.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class BobogouApplication {

    public static void main(String[] args) {
        SpringApplication.run(BobogouApplication.class, args);
    }

}
