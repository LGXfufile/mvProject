package com.simba.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


/*
@Date 2022/10/23 17:10
@PackageName com.simba.service.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

public class VideoToolsImplTest {

    @Test
    public void videoDownload(){
        final VideoToolsImpl videoTools = new VideoToolsImpl();
        String url = "https://baishi.xiaodutv.com/watch/08684399743225605219.html?fr=baishi.xiaodutv.com&tabfr=/channel/short/newamuse";
        videoTools.videoDownload(url);
    }

    @Test
    public void getUrl() throws Exception {
        // bili-video-card__wrap __scale-wrap
        String url = "https://search.bilibili.com/all?vt=16020262&keyword=%E8%80%81%E4%B8%AD%E5%8C%BB&from_source=webtop_search&spm_id_from=333.1007&search_source=5";
        URL url1 = new URL(url);

        final Document parse = Jsoup.parse(url1, 30000);

        final Document document = Jsoup.connect(url).get();
        System.out.println(document);
//        final Elements elementsByClass1 = document.select("div[class=video]");
//
//
//        System.out.println(elementsByClass1);


    }
}
