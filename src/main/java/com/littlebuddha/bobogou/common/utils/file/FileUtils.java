package com.littlebuddha.bobogou.common.utils.file;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-07-16
 * \* Time: 上午 10:51
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class FileUtils {

    /**
     * 单文件下载
     * @param response
     * @param filename
     * @param path
     */
    public static void download(HttpServletResponse response,String filename,String path ){
        if (filename != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(path);
                if (file.exists()) {
                    //设置Headers
                    response.setHeader("Content-Type","application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setHeader("Content-Disposition","attachment;filename=" +  new String( filename.getBytes("gb2312"), "ISO8859-1" ));
                    is = new FileInputStream(file);
                    bs =new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = bs.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                }else{
                    String error = Base64Util.encode("下载的文件资源不存在");
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }finally {
                try{
                    if(is != null){
                        is.close();
                    }
                    if( bs != null ){
                        bs.close();
                    }
                    if( os != null){
                        os.flush();
                        os.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载
     * @param response
     */
    public static void imgDownload(HttpServletResponse response, String [] names, String [] paths) {
        //存放--服务器上zip文件的目录
        String directory = "E:/usr/image/";
        File directoryFile = new File(directory);
        if (!directoryFile.isDirectory() && !directoryFile.exists()) {
            directoryFile.mkdirs();
        }
        //设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = formatter.format(new Date()) + ".zip";
        String strZipPath = directory + "\\" + zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try {
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < paths.length; i++) {
                //解码获取真实路径与文件名
                String realFileName = java.net.URLDecoder.decode(names[i], "UTF-8");
                String realFilePath = java.net.URLDecoder.decode(paths[i], "UTF-8");
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if (file.exists()) {
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */
                    ZipEntry zipEntry = new ZipEntry(realFileName);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while ((read = bufferStream.read(buf, 0, 1024 * 10)) != -1) {
                        zipStream.write(buf, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bufferStream) bufferStream.close();
                if (null != zipStream) {
                    zipStream.flush();
                    zipStream.close();
                }
                if (null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if (zipFile.exists()) {
            download(response, zipFileName, strZipPath);
            zipFile.delete();
        }
    }
}