package com.simba.service.impl;

/*
@Date 2022/10/22 22:37
@PackageName com.simba.service.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.service.VideoTools;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoToolsImpl implements VideoTools {
    public static final String baseCmd = "you-get -o E:\\youGet ";

    String path = "E:\\youGet";

    @Override
    public void videoDownload(String url) {
        try {
            Runtime mt = Runtime.getRuntime();
            String cmd = baseCmd + url;
            System.out.println("开始执行指令：" + cmd);
            Process pro = mt.exec(cmd);
            InputStream inputStream = pro.getInputStream();
            InputStream ers = pro.getErrorStream();
            System.out.println(inputStream);
            pro.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deleteFile();
        showFiles();
    }

    public int deleteFile() {
        int count = 0;
        final File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isFile()) {
                    String name = file1.getName();
                    int indexOf = name.lastIndexOf(".");
                    final String houzhuiOfFile = name.substring(indexOf);
                    if (!".mp4".equals(houzhuiOfFile)) {
                        //判断文件类型，如果不是.mp4结尾的，就删除文件；
                        file1.delete();
                        if (file1.exists()) {
                            System.out.println(file1 + ", 删除失败");
                        } else {
                            System.out.println(file1 + ",删除成功");
                            count++;
                        }
                    }
                }
            }
        }
        if (count > 0) {
            System.out.println("共计删除文件," + count + "个");
        } else {
            System.out.println("未删除任何文件");
        }
        return count;
    }

    public void showFiles(){
        final File file = new File(path);
        System.out.println("==============================");
        System.out.println("目录下包含文件：");
        if (file.isDirectory()){
            final File[] files = file.listFiles();
            for (File file1 : files) {
                System.out.println(file1.getName());
            }
        }
    }
}
