package com.simba.tools;

/*
@Date 2022/12/6 12:23
@PackageName com.simba.tools
@Author liguangxin
@Descrption 公共工具类
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import cn.hutool.core.lang.id.NanoId;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Date now = new Date(); // 创建一个Date对象，获取当前时间
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(strDateFormat);
        return f.format(now);
    }

    /**
     * 获取随机ID
     *
     * @return
     */
    public static String getRandomId() {
        return NanoId.randomNanoId(30);
    }
}
