package com.simba.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.simba.bean.VideoRequestDto;
import com.simba.service.UploadFileWriteService;
import com.simba.service.VideoTools;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;

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
public class VideoController extends HttpServlet {

    @Autowired
    UploadFileWriteService uploadFileWriteService;
    @Resource
    VideoTools videoTools;

    /**
     * 实现视频上传功能；
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public Map<String, String> uploadFile(MultipartFile file) throws Exception {
        return uploadFileWriteService.save(file);
    }

    /**
     * 视频转音频
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/videoToMusicReq")
    public String videoToMusicReq(MultipartFile file) throws Exception {
        return uploadFileWriteService.videoToMusic(file);
    }

    @PostMapping("/videoToText")
    public String videoToText(MultipartFile file) throws Exception {
        return uploadFileWriteService.videoToText(file);
    }

    @PostMapping("/videoDownload")
    public void videoDownload(@RequestBody VideoRequestDto videoRequestDto) throws Exception {
        videoTools.videoDownload(videoRequestDto.getVideoTargetUrl());
    }

    @GetMapping("/getCookiesaaa")
    public String getCookies(HttpServerRequest request){
        final Collection<HttpCookie> cookies = request.getCookies();
        System.out.println(cookies);
        return null;
    }

    @GetMapping("/getCookies")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Cookie[] cookies = req.getCookies();
        System.out.println(cookies);
    }
}
