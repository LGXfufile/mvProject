package com.simba.tools;

/*
@Date 2022/5/29 18:25
@PackageName com.simba.tools
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FormatDate {

    /**
     * 格式化输出当时时间，年月日，时分秒
     *
     * @return
     */
    public String dateFormatOfNow() {
        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式(年-月-日-时-分-秒)

        return dateFormat.format(now);//格式化然后放入字符串中
    }
}
