package com.littlebuddha.bobogou.modules.entity.system.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="logoinfo")
public class LogoInfo {

    @Value("${logoinfo.title}")
    private String title;

    @Value("${logoinfo.image}")
    private String image;

    @Value("${logoinfo.href}")
    private String href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
