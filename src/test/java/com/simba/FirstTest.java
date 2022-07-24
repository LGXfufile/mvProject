package com.simba;

/*
@Date 2022/6/1 23:14
@PackageName com.simba
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.tmp.MixedOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FirstTest {

    private MixedOperation mixedOperation;

    @BeforeEach
    public void init(){
        mixedOperation = new MixedOperation();
    }
    @Test
    public void sucessTest(){
        int result = mixedOperation.mixedOperation(4,2);
        Assertions.assertEquals(4,result);
    }

    public void failedTest(){
        ArithmeticException arithmeticException = Assertions.assertThrows(
                ArithmeticException.class, () -> mixedOperation.mixedOperation(4, 0)
        );
        Assertions.assertEquals("/ by zero",arithmeticException.getMessage());
    }
}
