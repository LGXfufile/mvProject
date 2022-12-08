package com.simba.bean;

/*
@Date 2022/12/5 0:26
@PackageName com.simba.bean
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.tools.CommonUtils;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
@Data
public class Book {

    private String id;

    private Integer bookId;

    private String name;

    private String type;

    private String desc;

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
