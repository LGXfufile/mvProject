package com.simba.controller;

/*
@Date 2022/12/8 22:11
@PackageName com.simba.controller
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.bean.SearchRequestDto;
import com.simba.bean.SearchResponseDto;
import com.simba.bean.SearchResultInfo;
import com.simba.service.SearchKeyWords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/keyWord")
public class SearchController {

    @Resource
    SearchKeyWords searchKeyWords;

    @RequestMapping("/getKeyWords")
    public SearchResponseDto getKeyWords(@RequestBody SearchRequestDto searchRequestDto) throws IOException {
        return searchKeyWords.queryAllExtendWords(searchRequestDto);
    }

    @RequestMapping("/writeKeyWordData")
    public void writeKeyWordData(@RequestBody SearchResultInfo searchResultInfo) throws Exception {
        searchKeyWords.writeKeyWords(searchResultInfo.getKeyWord());
    }

    @RequestMapping("/modifyKeyWord")
    public void modifyData(@RequestBody SearchResultInfo searchResultInfo)  {
        searchKeyWords.modifyData(searchResultInfo);
    }
}
