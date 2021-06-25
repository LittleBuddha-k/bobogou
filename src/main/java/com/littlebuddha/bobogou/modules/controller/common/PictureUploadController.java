package com.littlebuddha.bobogou.modules.controller.common;

import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.FileUtils;
import com.littlebuddha.bobogou.common.utils.MarkdownResult;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@RequestMapping("/file")
@Controller
public class PictureUploadController {

    @Autowired
    private GlobalSetting globalSetting;

    @ResponseBody
    @PostMapping("/picture")
    public Result pictureUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) throws IOException {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setMsg("文件为空");
        }
        //String uploadPath = request.getParameter("uploadPath");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = globalSetting.getUploadImage(); // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String domainName = globalSetting.getDomainName();
        String filename = filePath + "/" + fileName;
        result.put("url",domainName +filename);
        return result;
    }

    /**
     * markdown上传
     * @param request
     * @param response
     * @param file
     * @param model
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/markdownUpload")
    public MarkdownResult markdownUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) {
        MarkdownResult result = new MarkdownResult();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("editormd-image-file");
        if (multipartFile.isEmpty()) {
            result.setMessage("文件为空");
        }
        String fileName = multipartFile.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = globalSetting.getUploadImage(); // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(dest);
            String filename = filePath + "/" + fileName;
            result.setSuccess(1);
            String domainName = globalSetting.getDomainName();
            result.setUrl(domainName + filename);
            result.setMessage("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(0);
            result.setUrl("");
            result.setMessage("上传失败:"+e.getMessage());
        }
        return result;
    }
}
