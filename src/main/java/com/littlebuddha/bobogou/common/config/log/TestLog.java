package com.littlebuddha.bobogou.common.config.log;

import com.littlebuddha.bobogou.modules.entity.data.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestLog {

    private static final Logger LOG = LoggerFactory.getLogger(TestLog.class);

    @Bean
    public Goods logMethod() {
        LOG.info("==========print log==========");
        return new Goods();
    }
}
