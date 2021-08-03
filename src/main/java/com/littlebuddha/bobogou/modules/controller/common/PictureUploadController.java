package com.littlebuddha.bobogou.modules.controller.common;

import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.exception.errorcode.CustomizeErrorCode;
import com.littlebuddha.bobogou.common.exception.serviceexception.CustomizeException;
import com.littlebuddha.bobogou.common.utils.*;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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
        String uploadImage = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = file.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //将tif格式转化为jpg
        //saveFileName = saveFileName.replaceAll(".tif",".jpg");
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        uploadImage += data;
        File typeDir = new File(Paths.get(uploadImage).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(uploadImage, saveFileName).toUri());
        if (!file.isEmpty()) {
            try {
                file.transferTo(tempFile);
                String tifToJpg = TiffToJpg.tifToJpg(uploadImage + "/" + saveFileName);
                String url = "";
                if (tifToJpg != null && StringUtils.isNotBlank(tifToJpg)){
                    url = tifToJpg.replaceAll(globalSetting.getUploadImage(),globalSetting.getRootPath());
                }
                //String url = globalSetting.getRootPath() + data + "/" + saveFileName;
                result.put("url",url);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_EMPTY);
        }
        return result;
    }

    /**
     * 文件的上传
     * @param request
     * @param response
     * @param file
     * @param model
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public Result upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) throws IOException {
        Result result = new Result();
        String uploadImage = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = file.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        uploadImage += data;
        File typeDir = new File(Paths.get(uploadImage).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(uploadImage, saveFileName).toUri());
        if (!file.isEmpty()) {
            try {
                file.transferTo(tempFile);
                String url = globalSetting.getRootPath() + data + "/" +saveFileName;
                result.put("name",fileName);
                result.put("url",url);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_EMPTY);
        }
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
        String uploadImage = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = multipartFile.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        uploadImage += data;
        File typeDir = new File(Paths.get(uploadImage).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(uploadImage, saveFileName).toUri());
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
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_EMPTY);
        }
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
    @PostMapping("/goodsInfoMarkdownUpload")
    public MarkdownResult goodsInfoMarkdownUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) {
        MarkdownResult result = new MarkdownResult();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("editormd-image-file");
        String uploadImage = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = multipartFile.getOriginalFilename();
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        uploadImage += data;
        File typeDir = new File(Paths.get(uploadImage).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(uploadImage, saveFileName).toUri());
        if (!multipartFile.isEmpty()) {
            try {
                multipartFile.transferTo(tempFile);
                boolean size = FileUtils.checkImageElement(tempFile, 375, 375);
                if (size){
                    String url = globalSetting.getRootPath() + data + "/" + saveFileName;
                    result.setSuccess(1);
                    result.setUrl(url);
                    result.setMessage("上传成功");
                }else {
                    result.setSuccess(0);
                    result.setUrl("");
                    result.setMessage("商品详情必须为：" + 375 + "px " + " * " + 375 + " px,或者保持1：1比例,请重新确认图片分辨率正确后再次上传");
                }
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                result.setSuccess(0);
                result.setUrl("");
                result.setMessage("上传失败:"+e.getMessage());
            }
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_EMPTY);
        }
        return result;
    }

    @ResponseBody
    @PostMapping(value = "/upload-watermark")
    public Result uploadWatermark(MultipartFile file) {
        Result result = new Result();
        //先加水印
        String path = uploadWatermarkFile(file);
        String tifToJpg = TiffToJpg.tifToJpg(path.replaceAll(globalSetting.getRootPath(),globalSetting.getUploadImage()));
        tifToJpg = tifToJpg.replaceAll(globalSetting.getUploadImage(),globalSetting.getRootPath());
        result.put("url",tifToJpg);
        return result;
    }

    public String uploadWatermarkFile(MultipartFile file) {

        String uploadImage = globalSetting.getUploadImage();
        //上传文件名字
        String fileName = file.getOriginalFilename();
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //保存特殊名字
        String saveFileName = System.currentTimeMillis() + suffix;
        //年月日文件夹
        String data = DateUtils.localDateTimeToString(LocalDateTime.now(), DateUtils.FORMAT_FILE_NAME);
        uploadImage += data;
        File typeDir = new File(Paths.get(uploadImage).toUri());
        if (!typeDir.exists() && !typeDir.isDirectory()) {
            typeDir.mkdirs();
        }
        File tempFile = new File(Paths.get(uploadImage, saveFileName).toUri());
        if (!file.isEmpty()) {
            try {
                BufferedImage buffImage = ImageUtils.markWatermark(file);
                ImageIO.write(buffImage, suffix.replace(".", ""),tempFile);
                return globalSetting.getRootPath() + data + "/" + saveFileName;
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                throw new CustomizeException(CustomizeErrorCode.UPLOAD_ERROR);
            }
        }
        //文件为空
        throw new CustomizeException(CustomizeErrorCode.FILE_EMPTY);
    }
}
