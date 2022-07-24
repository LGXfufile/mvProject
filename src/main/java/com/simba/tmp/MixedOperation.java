package com.simba.tmp;

/*
@Date 2022/6/1 23:16
@PackageName com.simba
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

public class MixedOperation {
    public int mixedOperation(int x,int y){
        return division(x,y)+y;
    }

    private int division(int x, int y) {
        int result = (x/y);
        return result;
    }
    public int add(int x,int y){
        return x + y ;
    }
}
