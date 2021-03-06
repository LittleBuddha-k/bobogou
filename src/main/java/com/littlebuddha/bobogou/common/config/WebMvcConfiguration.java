package com.littlebuddha.bobogou.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image").addResourceLocations("file:E:/usr/image/");
        registry.addResourceHandler("/temp-rainy/").addResourceLocations("file:D:/temp-rainy/");
    }
}
