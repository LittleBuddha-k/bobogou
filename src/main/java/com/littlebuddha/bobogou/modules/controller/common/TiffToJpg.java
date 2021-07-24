package com.littlebuddha.bobogou.modules.controller.common;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.io.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-07-23
 * \* Time: 下午 02:00
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class TiffToJpg {

    /**
     * 将 tiff 转换 jpg 格式
     * @param filePath
     * @return
     */
    public static String tifToJpg(String filePath){
        String format = filePath.substring(filePath.lastIndexOf(".")+1);
        String turnJpgFile = filePath.replace("tif", "jpg");
        if(format.equals("tif")){
            File fileTiff = new File(turnJpgFile);
            if(fileTiff.exists()){
                //System.out.println("该tiff文件已经转换为 JPG 文件："+turnJpgFile);
                return turnJpgFile;
            }
            RenderedOp rd = JAI.create("fileload", filePath);//读取iff文件
            OutputStream ops = null;
            try {
                ops = new FileOutputStream(turnJpgFile);
                //文件存储输出流
                //JPEGEncodeParam param = new JPEGEncodeParam();
                //ImageEncoder image = ImageCodec.createImageEncoder("JPEG", ops, param); //指定输出格式
                //image.encode(rd );
                ImageIO.write(rd, "jpg", ops);
                //解析输出流进行输出
                ops.close();
                //System.out.println("tiff转换jpg成功:"+filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return turnJpgFile;
    }
}