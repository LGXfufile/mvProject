package com.simba.service.impl;

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
import com.simba.bean.ConvertInfo;
import com.simba.service.VideoConversionUtils;
import com.simba.tools.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoConversionUtilsImpl implements VideoConversionUtils {
    @Resource
    private MongoTemplate mongoTemplate;

    private final static String FFMPEGPATH = "E:\\Download\\ffmpeg.exe";

    private static String OUTPATHOFMUSIC = "src/main/resources/audio/";

    private static String txtFilePath = "src/main/resources/txfFile/";

//    private final static String INPUTPATH =  "src/main/resources/videos/840946180-1-208.mp4";

    private static final String APP_ID = "8c366cf9";
    private static final String SECRET_KEY = "3f6c57ab3d3dbe172cb3b29d30e4d035";

    public static final Map<ClassLoader, MultiValuedMap<String, String>> cathe =
            new ConcurrentReferenceHashMap<>();

    /**
     * 视频转音频
     *
     * @return
     */
    @Override
    public String videoToMusic(String INPUTPATH) throws Exception {
        return getResult(INPUTPATH);
    }

    public String getResult(String INPUTPATH) {
        final String outMusicPath = getOutpath();
        long l = System.currentTimeMillis();
        List<String> commend = new ArrayList<String>();
        commend.add(FFMPEGPATH);
        //表示覆盖输出文件
        commend.add("-y");
        //输入文件是和ffmpeg在同一目录下的1.avi文件，可以自己加路径，改名字
        commend.add("-i");
        commend.add(INPUTPATH);
        commend.add("-f");
        commend.add("wav");
        //声道
        commend.add("-ac");
        commend.add("1");
        //采样频率
        commend.add("-ar");
        commend.add("16000");
        //比特率
        commend.add("-b:v");
        commend.add("16K");
        commend.add(outMusicPath);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commend);
        builder.redirectErrorStream(true);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            log.error("视频转语音失败" + INPUTPATH);
            e.printStackTrace();
        }
        String cmdStr = Arrays.toString(commend.toArray()).replace(",", "");
        log.info("---开始执行FFmpeg指令：--- 执行线程名：" + builder.toString());
        log.info("---已执行的FFmepg命令：---" + cmdStr);
        // 取出输出流和错误流的信息
        // 注意：必须要取出ffmpeg在执行命令过程中产生的输出信息，如果不取的话当输出流信息填满jvm存储输出留信息的缓冲区时，线程就回阻塞住
        PrintStream errorStream = new PrintStream(process.getErrorStream());
        PrintStream inputStream = new PrintStream(process.getInputStream());
        errorStream.start();
        inputStream.start();
        // 等待ffmpeg命令执行完
        int exit = 0;
        try {
            // 等待进程执行结束
            exit = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("======视频转语音结果" + (exit == 0 ? true : false));
        long l1 = System.currentTimeMillis();
        log.info("======视频转语音用时：" + (l1 - l));
        saveConvertInfo(INPUTPATH, outMusicPath);
        return outMusicPath;
    }

    /**
     * 保存视频转换记录
     *
     * @param inputpath
     * @param outMusicPath
     */
    public void saveConvertInfo(String inputpath, String outMusicPath) {
        final Query query = new Query();
        final Criteria criteria = Criteria.where("outPath").is(outMusicPath);
        query.addCriteria(criteria);
        ConvertInfo one = mongoTemplate.findOne(query, ConvertInfo.class);
        if (one == null) {
            one = new ConvertInfo();
            one.init();
        }
        one.setInputPath(inputpath);
        one.setOutputPath(outMusicPath);
        one.setDesc("movie to music");
        one.update();
        mongoTemplate.save(one);
    }

    static class PrintStream extends Thread {
        java.io.InputStream __is = null;

        public PrintStream(java.io.InputStream is) {
            __is = is;
        }

        @Override
        public void run() {
            try {
                while (this != null) {
                    int _ch = __is.read();
                    if (_ch != -1) {
                        System.out.print((char) _ch);
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String musicToText(String INPUTMUSICPATH) throws Exception {

        //1、创建客户端实例
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
        //2、上传
        if (INPUTMUSICPATH == null) {
            System.out.println("路径为空~");
            return "获取路径为空";
        }
        Message task = lfasrClient.upload(INPUTMUSICPATH);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        String returnResult = "";
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            if (object != null) {
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
        return saveVideoToTxtInfo(returnResult);
    }

    /**
     * 保存转化记录
     *
     * @param returnResult
     * @throws IOException
     * @return
     */
    private String saveVideoToTxtInfo(String returnResult) throws IOException {

        String randomId = CommonUtils.getRandomId();
        String path = "src/main/resources/txfFile/"+randomId+".txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(returnResult);
        System.out.println("end");
        bufferedWriter.close();
        return path;

    }

    /**
     * 获取输出路径
     *
     * @return
     */
    public String getOutpath() {
        final String substring = UUID.randomUUID().toString().substring(0, 8);
        String resultPath = OUTPATHOFMUSIC + substring + ".m4a";
        return resultPath;
    }
}
