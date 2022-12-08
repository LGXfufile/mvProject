package com.simba;

/*
@Date 2022/6/1 23:14
@PackageName com.simba
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.junit.jupiter.api.Test;

import java.net.URL;


public class FirstTest {


    @Test
    public void getResult() throws Exception {
        String url = "https://mmzztt.com/photo/tag/meitui/";
        URL targetUrl = new URL(url);

        Document parse = Jsoup.parse(targetUrl, 10000);
        Elements elementsByClass = parse.getElementsByClass("uk-grid-margin");
        System.out.println(elementsByClass);


    }
}
