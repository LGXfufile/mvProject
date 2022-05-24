package com.simba.service.impl;

/*
@Date 2022/5/23 12:04
@PackageName com.xin.tmp.service
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.simba.mapper.UserMapper;
import com.simba.pojo.User;
import com.simba.service.FileDataOpes;
import com.simba.tools.EncodingDetect;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class FileDataOpesImpl implements FileDataOpes {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserMapper userMapper;
    int countOfTxt = 0;
    int countOfDocx = 0;


    @Override
    public void insertDataBatch(File srcFile) throws IOException {
        if (!srcFile.exists()) {
            return;
        }
        File fa[] = srcFile.listFiles();//用数组接收  F:\笔记总结\C#, F:\笔记总结\if语句.txt
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");//如果是目录就输出
                insertDataBatch(fs);
            } else {
                //判断文件后缀名是txt，则输出，如果不是，过滤掉；
                String fileName = fs.getName();
                int indexOf = fileName.lastIndexOf(".");
                String wenjianhouzhui = fileName.substring(indexOf + 1);
                if (fs!=null&&fs.length()!=0&&"txt".equals(wenjianhouzhui)) {
                    countOfTxt++;
                //如果不是utf8格式，则转为utf8
                    String absolutePath = fs.getAbsolutePath();
                    System.out.println(absolutePath);
                    String fileEncode = EncodingDetect.getJavaEncode(absolutePath);
                    System.out.println("该文件编码是： "+fileEncode);
                    if ("GB2312".equals(fileEncode)) {
//          调用转码方法
                        convertCharset(absolutePath, Charset.forName("GB2312"), Charset.forName("UTF-8"), null);
                    }else if ("GBK".equals(fileEncode)){
                        convertCharset(absolutePath, Charset.forName("GBK"), Charset.forName("UTF-8"), null);
                    }
                    fileEndWithTxtInsertTool(fs);
                } else if (fileName.endsWith(".docx")) {
                    countOfDocx++;
                    fileEndWithDocxInsertTool(fs);
                }else {
                    System.out.println("文件名是--->"+fileName);
                }
            }

        }
        System.out.println("总共统计txt文件：" + countOfTxt + "个，非txt文件：" + countOfDocx + "个");

    }

    private void convertCharset(String path, Charset fromCharset, Charset toCharset, String expansion) {
        if (StrUtil.isBlank(path)) {
            return;
        }
        File file = FileUtil.file(path);
        FileUtil.convertCharset(file, fromCharset, toCharset);
    }

    private void saveAsUTF8(String absolutePath) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath), "UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePath), "UTF-8"));
        int i = 0;
        String str = "";
        while ((str = br.readLine()) != null) {
            if (i == 0)//读取第一行，将前三个字节去掉，重新new个String对象
            {
                byte[] bytes = str.getBytes("UTF-8");
                str = new String(bytes, 3, bytes.length - 3);
                bw.write(str+"\r\n");
                i++;
            } else
                bw.write(str+"\r\n");
        }
        br.close();
        bw.close();
    }


    private void fileEndWithTxtInsertTool(File fs) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fs));//构造一个BufferedReader类来读取文件
        String s = null;
        while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
            result.append(System.lineSeparator() + s);
        }
        String fileName = fs.getName();
        int indexOf = fileName.lastIndexOf(".");
        String fileNothouzhui = fileName.substring(0, indexOf);
        User user = new User(countOfTxt, fileNothouzhui, result.toString());
        if (userMapper.addUser(user)) {
            System.out.println("插入成功:" + countOfTxt);
        }
    }



    /**
     *
     * @param srcFile 如果文件后缀名是docx，插入数据库工具
     * @throws IOException
     */
    private void fileEndWithDocxInsertTool(File srcFile) throws IOException {
        String absolutePath = srcFile.getAbsolutePath();
        System.out.println(absolutePath);
        FileInputStream fis = new FileInputStream(absolutePath);
        XWPFDocument xdoc = new XWPFDocument(fis);
        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        String context = extractor.getText();
//                        System.out.println(context);
        int indexof = srcFile.getName().lastIndexOf(".");
        String fileNothouzhui = srcFile.getName().substring(0,indexof);
        User user = new User(countOfDocx, fileNothouzhui, context);
        if (userMapper.addUser(user)) {
            System.out.println("插入成功:" + countOfDocx);
        }
        fis.close();
    }

    @Override
    public List<User> queryUserById (User user){

        List<User> users = userMapper.queryUserById(user);
        return users;
    }
    @Override
    public List<User> queryUserAll () {

        List<User> users = userMapper.queryUserAll();
        return users;
    }
}
