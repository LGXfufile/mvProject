package com.simba.service;

/*
@Date 2022/12/8 22:13
@PackageName com.simba.service
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.bean.SearchRequestDto;
import com.simba.bean.SearchResponseDto;
import com.simba.bean.SearchResultInfo;

import java.io.IOException;
import java.util.Map;


public interface SearchKeyWords {
    SearchResponseDto queryAllExtendWords(SearchRequestDto searchRequestDto) throws IOException;
    SearchResponseDto queryKeyWords(SearchRequestDto searchRequestDto);
    Map<String, String> writeKeyWords(String keyWord) throws Exception;
    void modifyData(SearchResultInfo searchResultInfo);
}
