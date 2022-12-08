package com.simba.service;

/*
@Date 2022/10/12 14:26
@PackageName com.simba.service
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadFileWriteService {
    Map<String, String> save(MultipartFile file);
    String videoToMusic(MultipartFile file) throws Exception;
    String videoToText(MultipartFile file) throws Exception;
}
