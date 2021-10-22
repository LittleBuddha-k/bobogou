package com.littlebuddha.bobogou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class BobogouApplication {

    public static void main(String[] args) {
        SpringApplication.run(BobogouApplication.class, args);
    }

}
