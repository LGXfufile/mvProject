package com.simba.pojo;

/*
@Date 2022/5/24 10:09
@PackageName com.simba.pojo
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class aaaa {


    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("b");
        set.add("c");
        System.out.println(StringUtils.join(set.toArray(), ","));
    }
    public void vvv(){



    }
}
