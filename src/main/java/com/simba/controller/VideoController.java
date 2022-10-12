package com.simba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/*
@Date 2022/10/11 23:47
@PackageName com.simba.controller
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class VideoController {

    public static final String FILE_PATH = "E:\\javaLearn\\JavaLearning\\simba\\src\\main\\resources\\static\\";

    /**
     * 实现视频上传功能；
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        System.out.println(originalFilename);
        final byte[] bytes = new byte[1024];
        try {
            String absolutePath = FILE_PATH + originalFilename;
            fos = new FileOutputStream(absolutePath);
            fis = (FileInputStream) file.getInputStream();
            int read = fis.read(bytes);
            while (read!=-1){
                fos.write(bytes,0,bytes.length);
                read = fis.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }finally {
            try {
                fos.close();
                fis.close();
                return "200";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "200";
    }
}
