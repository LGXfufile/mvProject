package com.simba.videoConversion.impl;

/*
@Date 2022/9/25 11:36
@PackageName com.simba.videoConversion.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;
import com.simba.videoConversion.VideoConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoConversionUtilsImpl implements VideoConversionUtils {

    private final static String FFMPEGPATH = "E:\\Download\\ffmpeg.exe";

    private static String OUTPATHOFMUSIC = "src/main/resources/audio/";

//    private final static String INPUTPATH =  "src/main/resources/videos/840946180-1-208.mp4";

    private static final String APP_ID = "8c366cf9";
    private static final String SECRET_KEY = "3f6c57ab3d3dbe172cb3b29d30e4d035";

    /**
     * 视频转音频
     *
     * @return
     */
    @Override
    public String videoToMusic(String INPUTPATH) throws Exception {

        final String outMusicPath = getOutpath();
        Runtime runtime = Runtime.getRuntime();
        String cut = FFMPEGPATH + " -i " + INPUTPATH + " -vn -codec copy " + outMusicPath;
        System.out.println(cut);
        log.info("视频,{},转音频任务完成,输出路径,{}", INPUTPATH, outMusicPath);
        runtime.exec(cut);
        return outMusicPath;
    }

    @Override
    public String musicToText(String INPUTMUSICPATH) throws Exception {

        //1、创建客户端实例
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
        //2、上传
        Message task = lfasrClient.upload(INPUTMUSICPATH);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        String returnResult = "";
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            if (object != null){
                status = object.getInteger("status");
                System.out.println(message.getData());
                TimeUnit.SECONDS.sleep(2);
            }
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        String data = result.getData();

        final JSONArray array = JSONArray.parseArray(data);
        StringBuffer buffer = new StringBuffer();
        final StringBuffer buffer1 = new StringBuffer();
        buffer1.append(",");
        buffer1.append(".");
        buffer1.append("，");
        buffer1.append("。");
        buffer1.append("?");
        buffer1.append("？");
        for (int i = 0; i < array.size(); i++) {

            final String s1 = array.get(i).toString();

            log.info("分片结果,{}", s1);

            final JSONObject jsonObject = JSON.parseObject(s1);
            String onebest = jsonObject.get("onebest").toString().trim();
            if (onebest.length() > 1) {
                String substring = onebest.substring(onebest.length() - 1);
                if (!buffer1.toString().contains(substring)) {
                    log.info("{},后面加个标点", onebest);
                    onebest += ",";
                }
                buffer.append(onebest);
            }

        }
        returnResult = buffer.toString();
        log.info("音频转文字任务结束,输入,{},输出,\n{}", INPUTMUSICPATH, buffer.toString());
        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
        log.info("返回了吗？");
        return returnResult;

    }


    /**
     * 获取输出路径
     *
     * @return
     */
    public String getOutpath() {
        final String substring = UUID.randomUUID().toString().substring(0, 8);
        OUTPATHOFMUSIC = OUTPATHOFMUSIC + substring + ".m4a";
        return OUTPATHOFMUSIC;
    }
}
