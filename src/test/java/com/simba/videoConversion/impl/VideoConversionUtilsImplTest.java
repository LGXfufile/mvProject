package com.simba.videoConversion.impl;

import com.simba.tools.TextToSpeech;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

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

    private final static String INPUTPATH = "src/main/resources/videos/840946180-1-208.mp4";

    @Test
    public void VideoToMusic() throws Exception {
        final VideoConversionUtilsImpl videoConversionUtils = new VideoConversionUtilsImpl();
        final String outMusicPath = videoConversionUtils.videoToMusic(INPUTPATH);
        TextToSpeech.textToSpeech("恭喜老板，任务完成啦~~~好开心");
        Assert.assertNotNull(outMusicPath);
    }

    @Test
    public void musicToText() throws Exception {
        final VideoConversionUtilsImpl videoConversionUtils = new VideoConversionUtilsImpl();
        String inputMusicPath = "src/main/resources/audio/1c75f9cb.m4a";
        final String s = videoConversionUtils.musicToText(inputMusicPath);
        log.info("输出结果,{}", s);
        TextToSpeech.textToSpeech("恭喜老板，任务完成");
    }

    /**
     * 语音播报测试；
     * 问题解决参考：
     * https://blog.csdn.net/hodge11/article/details/109527129
     */
    @Test
    public void yuyinBobaoTest() {
        try {
            TextToSpeech.textToSpeech("恭喜老板，任务完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
