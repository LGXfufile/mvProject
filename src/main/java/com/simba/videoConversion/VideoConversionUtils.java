package com.simba.videoConversion;

/*
@Date 2022/9/25 11:35
@PackageName com.simba.videoConversion
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

public interface VideoConversionUtils {
    String videoToMusic(String INPUTPATH) throws Exception;
    String musicToText(String INPUTMUSICPATH) throws Exception;
}
