package com.littlebuddha.bobogou.modules.entity.system.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 首页信息
 */
@Component
@ConfigurationProperties(prefix ="homeinfo")
public class HomeInfo {

    @Value("${homeinfo.title}")
    private String title;

    @Value("${homeinfo.href}")
    private String href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
