package com.littlebuddha.bobogou.modules.controller.common;

import com.littlebuddha.bobogou.common.utils.FileUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@RequestMapping("/data/banner")
@Controller
public class PictureUploadController {

    @ResponseBody
    @PostMapping("/pictureUpload")
    public Result pictureUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, Model model) throws IOException {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setMsg("文件为空");
        }
        //String uploadPath = request.getParameter("uploadPath");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
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
        String filename = "/temp-rainy/" + fileName;
        result.put("url","/bobogou"+filename);
        return result;
    }
}
