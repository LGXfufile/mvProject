package com.simba.controller;

/*
@Date 2022/5/22 21:34
@PackageName com.xin.controller
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import com.simba.bean.YingShi;
import com.simba.pojo.User;
import com.simba.service.FileDataOpes;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class Hello {

    @Autowired
    FileDataOpes dataOpes;

    /**
     * 向数据库导数据
     * @return
     * @throws IOException
     */
    @GetMapping({"/hi",""})
    public HashMap<Object, Object> batchInsertData() throws IOException {

        String path = "D:\\Download"; // 路径
        File file = new File(path);//获取路径  F:\测试目录

        dataOpes.insertDataBatch(file);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data","success");
        return map;

    }

    @PostMapping("/selectOne")
    public HashMap<Object, Object> selectOne(@RequestBody YingShi yingShi){
        HashMap<Object, Object> map = new HashMap<>();
        if (yingShi.getTitle()==null||yingShi.getTitle().length()==0){
            System.out.println("没有输入");
        }else {
            System.out.println("来了");
            User user = new User();
            user.setTitle(yingShi.getTitle());
            System.out.println(user.toString());

            List<User> users = dataOpes.queryUserById(user);
//        String result = JSONUtil.toJsonStr(users);
//        System.out.println(result);

            map.put("data",users);
            map.put("code","sucess");
            return map;
        }

        map.put("data","null");
        map.put("code","failed");
        return map;
    }

    @GetMapping("/selectAll")
    public HashMap<Object, Object> selectAll(){
        HashMap<Object, Object> map = new HashMap<>();
        List<User> users = dataOpes.queryUserAll();
//        String result = JSONUtil.toJsonStr(users);
//        System.out.println(result);

        map.put("data",users);
        map.put("code","sucess");
        return map;
    }


    @PostMapping("/exportData")
    public HashMap<Object, Object> exportData(@RequestBody YingShi yingShi){
        HashMap<Object, Object> map = new HashMap<>();
        User user = new User();
        user.setTitle(yingShi.getTitle());
        List<User> users = dataOpes.queryUserById(user);
//        String result = JSONUtil.toJsonStr(users);
//        System.out.println(result);

        map.put("data",users);
        map.put("data","进来了");
        map.put("code","sucess");
        return map;
    }

    /**
     *  导出
     * @param response
     * @throws Exception
     * @return
     */
    @GetMapping("/export")
    public HashMap<Object, Object> export(HttpServletResponse response)  throws Exception{
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet("sheet1");
        String fileName = "学生列表.xls"; // 设置要导出的文件的名字

        HashMap<Object, Object> map = new HashMap<>();
        User user = new User();

        user.setTitle("互换身");
        List<User> users = dataOpes.queryUserById(user);
        // 获取数据集合

        // 生成标题行
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("111");
        row.createCell(1).setCellValue("222");
        row.createCell(2).setCellValue("333");

        for (int i = 0; i < 3; i++) {

            row = sheet.createRow(i+1); // 从第2行开始填充数据
            // 序号
            row.createCell(0).setCellValue(users.toString());

        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();

        workbook.write(response.getOutputStream());
        map.put("data",users);
        map.put("data","进来了");
        map.put("code","sucess");
        return map;
    }


}
