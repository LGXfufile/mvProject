package com.simba.service.impl;

/*
@Date 2022/10/12 14:26
@PackageName com.simba.service.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.service.UploadFileWriteService;
import com.simba.service.VideoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadFileWriteServiceImpl implements UploadFileWriteService {
    public static final String FILE_PATH = "E:\\javaLearn\\JavaLearning\\simba\\src\\main\\resources\\file\\";

    @Autowired
    VideoConversionUtils videoConversionUtils;
    @Override
    public Map<String, String> save(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        originalFilename = getCommonFileName(originalFilename);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        System.out.println(originalFilename);
        final byte[] bytes = new byte[1024];
        String absolutePath = null;
        try {
            absolutePath = FILE_PATH + originalFilename;
            fos = new FileOutputStream(absolutePath);
            fis = (FileInputStream) file.getInputStream();
            int read = fis.read(bytes);
            while (read!=-1){
                fos.write(bytes,0,bytes.length);
                read = fis.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code","500");
            return map;
        }finally {
            try {
                fos.close();
                fis.close();
                map.put("code","200");
                map.put("success","true");
                map.put("filePath",absolutePath);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("code","200");
        map.put("success","true");
        map.put("filePath",absolutePath);
        return map;
    }

    /**
     * 转换文件名称，防止因为中文名称导致转换失败；
     * @param originalFilename
     * @return
     */
    private static String getCommonFileName(String originalFilename) {
        int indexOf = originalFilename.lastIndexOf(".");
        String substring = UUID.randomUUID().toString().substring(0, 16);
        originalFilename = "demo-"+substring+ originalFilename.substring(indexOf);
        return originalFilename;
    }

    @Override
    public String videoToMusic(MultipartFile file) throws Exception {
        Map<String, String> save = save(file);
        String INPUTPATH = save.get("filePath");
        String outMusicPath = videoConversionUtils.videoToMusic(INPUTPATH);
        return outMusicPath;
    }

    @Override
    public String videoToText(MultipartFile file) throws Exception {
        Map<String, String> save = save(file);
        String INPUTPATH = save.get("filePath");
        String outMusicPath = videoConversionUtils.videoToMusic(INPUTPATH);
        return videoConversionUtils.musicToText(outMusicPath);
    }
}
