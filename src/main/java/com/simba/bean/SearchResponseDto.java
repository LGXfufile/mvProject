package com.simba.bean;

import lombok.Data;

import java.util.List;

/*
@Date 2022/12/11 18:20
@PackageName com.simba.bean
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/
@Data
public class SearchResponseDto {

    private Integer pageSize;

    private Integer currentPage;

    private Long totalCount;

    private Integer totalPage;

    private List items;
}
