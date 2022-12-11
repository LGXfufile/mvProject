package com.simba.bean;

/*
@Date 2022/12/11 18:12
@PackageName com.simba.controller
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import lombok.Data;

@Data
public class SearchRequestDto {
    private String keyWord;
    private Integer pageSize;
    private Integer currentPage;
}
