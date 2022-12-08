package com.simba.bean;

/*
@Date 2022/12/5 23:04
@PackageName com.simba.tools.CookiesUtils.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.tools.CommonUtils;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.servlet.http.Cookie;
import java.util.Map;

@Document(collection = "cookies")
@Data
public class CookieDao {

    private String id;

    private String name;

    private Map<String, Cookie> value;

    private String createTime;

    private String updateTime;

    private Integer fg;

    public void init() {
        this.id = CommonUtils.getRandomId();
        this.createTime = CommonUtils.getCurrentTime();
        this.updateTime = CommonUtils.getCurrentTime();
        this.fg = 1;
    }

    public void update() {
        this.updateTime = CommonUtils.getCurrentTime();
    }
}
