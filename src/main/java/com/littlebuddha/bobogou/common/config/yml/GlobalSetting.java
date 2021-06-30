package com.littlebuddha.bobogou.common.config.yml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-25
 * \* Time: 上午 11:22
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * 全局配置类
 */
@Component
@ConfigurationProperties(prefix ="global")
public class GlobalSetting {

    @Value("${global.uploadPath}")
    private String uploadImage;//图片全局配置保存上传路径

    @Value("${global.domainName}")
    private String rootPath;//rootPath

    public String getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(String uploadImage) {
        this.uploadImage = uploadImage;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}