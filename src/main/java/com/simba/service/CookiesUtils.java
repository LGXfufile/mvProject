package com.simba.service;

/*
@Date 2022/12/5 22:57
@PackageName com.simba.tools.CookiesUtils
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CookiesUtils {

    Map<String, Cookie> getCookies(HttpServletRequest request) throws Exception;
}
