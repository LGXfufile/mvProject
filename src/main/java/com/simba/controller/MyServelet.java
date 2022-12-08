package com.simba.controller;

/*
@Date 2022/12/5 22:34
@PackageName com.simba.controller
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.service.CookiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Cookie:
 * jenkins-timestamper-offset=-28800000;
 * Idea-6d4157dd=38bf378c-6441-4058-b86b-9c2c2e85a056;
 * Webstorm-30429de3=53b47553-a109-464d-a59f-81b25f95dd4e;
 * NMTID=00OlP9bsR9Dr30miExBp416H4rVaZoAAAGAddsxlA;
 * _ga=GA1.1.1500574185.1666500979
 *
 *
 * {jenkins-timestamper-offset=javax.servlet.http.Cookie@8ac82d9,
 * _ga=javax.servlet.http.Cookie@66b7dbc,
 * Webstorm-30429de3=javax.servlet.http.Cookie@254e9a9,
 * Idea-6d4157dd=javax.servlet.http.Cookie@47567787,
 * NMTID=javax.servlet.http.Cookie@2b809ffe}
 */

@Controller
public class MyServelet {

    @Autowired
    private CookiesUtils cookiesUtils;
    @GetMapping("/test")
    public String test(HttpServletRequest request) throws Exception {
        final Map<String, Cookie> cookies = cookiesUtils.getCookies(request);
        System.out.println(cookies.toString());

        return "index";
    }
}

