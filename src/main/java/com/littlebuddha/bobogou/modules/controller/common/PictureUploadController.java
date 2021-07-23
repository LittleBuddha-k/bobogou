package com.littlebuddha.bobogou.modules.controller.common;

import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.*;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;
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
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
        String rootPath = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = file.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //将tif格式转化为jpg
        //saveFileName = saveFileName.replaceAll(".tif",".jpg");
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        rootPath += data;
        File typeDir = new File(Paths.get(rootPath).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(rootPath, saveFileName).toUri());
        if (!file.isEmpty()) {
            try {
                file.transferTo(tempFile);
                String tifToJpg = TiffToJpg.tifToJpg(rootPath + "/" + saveFileName);
                String url = "";
                if (tifToJpg != null && StringUtils.isNotBlank(tifToJpg)){
                    url = tifToJpg.replaceAll(globalSetting.getUploadImage(),globalSetting.getRootPath());
                }
                //String url = globalSetting.getRootPath() + data + "/" + saveFileName;
                result.put("url",url);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        }
        /*if (file.isEmpty()) {
            result.setMsg("文件为空");
        }
        //String uploadPath = request.getParameter("uploadPath");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //String filePath = globalSetting.getUploadImage(); // 上传后的路径
        String filePath = "D://temp-rainy//"; // 上传后的路径
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
        String domainName = globalSetting.getRootPath();
        String filename = "/temp-rainy/" + fileName;
        result.put("url","/bobogou" +filename);*/
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
        String rootPath = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = multipartFile.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        rootPath += data;
        File typeDir = new File(Paths.get(rootPath).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(rootPath, saveFileName).toUri());
        if (!multipartFile.isEmpty()) {
            try {
                multipartFile.transferTo(tempFile);
                String url = globalSetting.getRootPath() + data + "/" + saveFileName;
                result.setSuccess(1);
                result.setUrl(url);
                result.setMessage("上传成功");
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                result.setSuccess(0);
                result.setUrl("");
                result.setMessage("上传失败:"+e.getMessage());
            }
        }
        /*if (multipartFile.isEmpty()) {
            result.setMessage("文件为空");
        }
        String fileName = multipartFile.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //String filePath = globalSetting.getUploadImage(); // 上传后的路径
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(dest);
            //String filename = filePath + "/" + fileName;
            String filename = "/temp-rainy/" + fileName;
            result.setSuccess(1);
            String domainName = globalSetting.getRootPath();
            //result.setUrl(domainName + filename);
            result.setUrl("/bobogou" + filename);
            result.setMessage("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(0);
            result.setUrl("");
            result.setMessage("上传失败:"+e.getMessage());
        }*/
        return result;
    }

    /**
     * 上传文件时
     * @param request
     * @param response
     * @param file
     * @param model
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public Result upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) {
        Result result = new Result();
        //String uploadPath = request.getParameter("uploadPath");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //String filePath = globalSetting.getUploadImage(); // 上传后的路径
        String filePath = "D://temp-rainy//"; // 上传后的路径
        //fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String domainName = globalSetting.getRootPath();
        result.put("name",fileName);
        String filename = "/temp-rainy/" + fileName;
        result.put("url","/bobogou" +filename);
        return result;
    }

    /**
     * 下载文件时
     * @param request
     * @param response
     * @param file
     * @param model
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/download")
    public Result download(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) {
        Result result = new Result();
        //String uploadPath = request.getParameter("uploadPath");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //String filePath = globalSetting.getUploadImage(); // 上传后的路径
        String filePath = "D://temp-rainy//"; // 上传后的路径
        //fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String domainName = globalSetting.getRootPath();
        result.put("name",fileName);
        String filename = "/temp-rainy/" + fileName;
        result.put("url","/bobogou" +filename);
        return result;
    }
}
