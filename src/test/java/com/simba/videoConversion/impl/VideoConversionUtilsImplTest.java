package com.simba.videoConversion.impl;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.simba.videoConversion.VideoConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.geometry.spherical.oned.S1Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;


/*
@Date 2022/9/25 11:40
@PackageName com.simba.videoConversion.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/
@Slf4j
class VideoConversionUtilsImplTest {

    private final static String INPUTPATH =  "src/main/resources/videos/840946180-1-208.mp4";
//
//    @Autowired
//    VideoConversionUtils videoConversionUtils;

    @Test
    public void VideoToMusic() throws Exception {
        final VideoConversionUtilsImpl videoConversionUtils = new VideoConversionUtilsImpl();
        final String outMusicPath = videoConversionUtils.videoToMusic(INPUTPATH);


        final String s = videoConversionUtils.musicToText(outMusicPath);
        log.info("输出结果,{}",s);
    }

    @Test
    public void musicToText() throws Exception {
        final VideoConversionUtilsImpl videoConversionUtils = new VideoConversionUtilsImpl();
        String inputMusicPath = "src/main/resources/audio/1c75f9cb.m4a";
        final String s = videoConversionUtils.musicToText(inputMusicPath);
        log.info("输出结果,{}",s);
    }

    @Test
    public void aaa(){
        String aa = "[ {\n" +
                "        \"bg\":\"15680\",\n" +
                "        \"ed\":\"17440\",\n" +
                "        \"onebest\":\"长夏逝去，\",\n" +
                "        \"speaker\":\"0\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"bg\":\"17440\",\n" +
                "        \"ed\":\"18710\",\n" +
                "        \"onebest\":\"杨秋悄然。\",\n" +
                "        \"speaker\":\"0\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"bg\":\"18960\",\n" +
                "        \"ed\":\"22640\",\n" +
                "        \"onebest\":\"秋天是人们心中最美的季节，\",\n" +
                "        \"speaker\":\"0\"\n" +
                "    }]";
//        log.info(aa);
        final String s = JSON.toJSONString(aa);
        final JSONArray array = JSONArray.parseArray(aa);
        final StringBuffer buffer = new StringBuffer();





        for (int i = 0; i < array.size(); i++) {

            final String s1 = array.get(i).toString();
            System.out.println(s1);

            final JSONObject jsonObject = JSON.parseObject(s1);
            String onebest = jsonObject.get("onebest").toString();
            onebest+=",";
            buffer.append(onebest);
        }
        System.out.println(buffer.toString());

    }
}
