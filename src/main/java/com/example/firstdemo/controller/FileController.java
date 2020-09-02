package com.example.firstdemo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response){
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(new File("src/main/resources/static/doc/项目新建及配置.doc"));
            download( response, inputStream, "测试下载文件.doc", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream == null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件下载
     * @param response
     * @param is 文件输入流
     * @param fileName 下载的文件名称
     * @param encoding 编码格式
     */
    public static void download(HttpServletResponse response, InputStream is, String fileName, String encoding){
        if(is == null || StringUtils.isBlank(fileName)){
            return;
        }

        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;

        try{
            bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            response.setContentType("application/octet-stream;charset=" + encoding);
            response.setCharacterEncoding(encoding);
            response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, encoding));
            byte[] buffer = new byte[1024];
            int len = bis.read(buffer);
            while(len != -1){
                bos.write(buffer, 0, len);
                len = bis.read(buffer);
            }

            bos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(bis != null){
                try{
                    bis.close();
                }catch(IOException e){}
            }

            if(is != null){
                try{
                    is.close();
                }catch(IOException e){}
            }
        }
    }
}
