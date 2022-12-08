package com.simba.service;

/*
@Date 2022/5/23 12:02
@PackageName com.xin.tmp.service
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import com.simba.bean.User;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface FileDataOpes {
    void insertDataBatch(File srcFile) throws IOException;
    List<User> queryUserById(User user);
    List<User> queryUserAll ();
}
