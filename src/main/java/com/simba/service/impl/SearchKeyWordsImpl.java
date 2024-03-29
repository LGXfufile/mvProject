package com.simba.service.impl;

/*
@Date 2022/12/8 22:13
@PackageName com.simba.service.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.simba.bean.KeyWordInfo;
import com.simba.bean.SearchRequestDto;
import com.simba.bean.SearchResponseDto;
import com.simba.bean.SearchResultInfo;
import com.simba.service.SearchKeyWords;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SearchKeyWordsImpl implements SearchKeyWords {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public SearchResponseDto queryAllExtendWords(SearchRequestDto searchRequestDto) throws IOException {

        SearchResponseDto response = new SearchResponseDto();
        final Query query = new Query();
        query.addCriteria(Criteria.where("fg").is(1));

        query.with(Sort.by(Sort.Order.desc("createTime")));
        if (StringUtils.hasText(searchRequestDto.getKeyWord())) {
            Pattern pattern = Pattern.compile("^.*" + searchRequestDto.getKeyWord() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(new Criteria().and("keyWord").regex(pattern));
        }
        if (StringUtils.hasText(searchRequestDto.getSearchKeyWord())) {
            Pattern pattern = Pattern.compile("^.*" + searchRequestDto.getSearchKeyWord() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(new Criteria().and("searchKeyWord").regex(pattern));
        }
        long totalCount = mongoTemplate.count(query, SearchResultInfo.class);

//        数据查询起始数
        query.skip((searchRequestDto.getCurrentPage() - 1) * searchRequestDto.getPageSize());
        query.limit(searchRequestDto.getPageSize());
        List<SearchResultInfo> searchResultInfos = mongoTemplate.find(query, SearchResultInfo.class);

        List<Object> list = new ArrayList<>();
        searchResultInfos.stream().forEach(x -> list.add(x.getSearchKeyWord()));

        log.info("查询结果,{}", list.toString());

        response.setTotalCount(totalCount);
        response.setCurrentPage(searchRequestDto.getCurrentPage());
        response.setPageSize(searchRequestDto.getPageSize());
        response.setItems(searchResultInfos);

        return response;
    }

    @Override
    public SearchResponseDto queryKeyWords(SearchRequestDto searchRequestDto) {
        SearchResponseDto response = new SearchResponseDto();
        final Query query = new Query();
        query.addCriteria(Criteria.where("fg").is(1));
        query.with(Sort.by(Sort.Order.desc("keyWord")));
        if (StringUtils.hasText(searchRequestDto.getKeyWord())) {
            Pattern pattern = Pattern.compile("^.*" + searchRequestDto.getKeyWord() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(new Criteria().and("keyWord").regex(pattern));
        }
        long totalCount = mongoTemplate.count(query, KeyWordInfo.class);

//        数据查询起始数
        query.skip((searchRequestDto.getCurrentPage() - 1) * searchRequestDto.getPageSize());
        query.limit(searchRequestDto.getPageSize());
        final List<KeyWordInfo> keyWordInfos = mongoTemplate.find(query, KeyWordInfo.class);

        response.setTotalCount(totalCount);
        response.setCurrentPage(searchRequestDto.getCurrentPage());
        response.setPageSize(searchRequestDto.getPageSize());
        response.setItems(keyWordInfos);

        return response;
    }

    @Override
    public Map<String, String> writeKeyWords(String keyWord) throws Exception {

        String arr[] = {"", "怎么", "如何", "哪里", "能", "快速", "怎么样",
                "是真的吗", "能不能", "是不是", "可不可以", "需不需要"};
        String subKeyWord;
        AtomicInteger count = new AtomicInteger();
        final long start = new Date().getTime();

        List<Object> list = new ArrayList<>();

        for (String s : arr) {
            for (int i = 0; i < 26; i++) {
                char c = (char) ('a' + i);
                subKeyWord = keyWord + s + c;
                String url = "https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd=" + subKeyWord.trim() + "&json=1";
                final Document parse = Jsoup.parse(new URL(url), 30000);
                String text = parse.text();
                String vv = "window.baidu.sug(";
                final String substring = text.substring(vv.length(), text.length() - 2);
                JSONObject jsonObject = null;
                if (substring.endsWith("}")){
                    jsonObject = JSONUtil.parseObj(substring);
                }else{
                    log.error("JSON解析异常，不处理，跳过，异常信息{}",substring);
                    continue;
                }
                final Object s1 = jsonObject.get("s");
                final JSONArray objects = JSONUtil.parseArray(s1);
                String finalSubKeyWord = subKeyWord;
                objects.stream().forEach(x -> {
                    log.info("{}", x);
                    list.add(x);
                    saveSearchInfo(keyWord, finalSubKeyWord, x);
                    saveKeyWord(keyWord);
                    count.getAndIncrement();
                });
            }
        }
        log.info("写入结果,{}",list.toString());
        long end = new Date().getTime();
        long ss = (end - start) / 1000;
        log.info("写入数据条数:{}", count);
        log.info("用时,{}秒", ss);
        Map<String, String> map = new HashMap<>();
        map.put("data", "success");
        map.put("code", "200");
        return map;
    }

    private void saveKeyWord(String keyWord) {
        final Query query = new Query();
        final Criteria criteria = Criteria.where("keyWord").is(keyWord);
        query.addCriteria(criteria);
        KeyWordInfo one = mongoTemplate.findOne(query, KeyWordInfo.class);
        if (one == null) {
            one = new KeyWordInfo();
            one.init();
            one.setKeyWord(keyWord);
            log.info("keyWordsInfo,初始化成功,{}", one.getKeyWord());
            mongoTemplate.save(one);
        }
        return;
    }

    @Override
    public void modifyData(SearchResultInfo searchResultInfo) {

        final Query query = new Query();
        final Criteria criteria = Criteria.where("searchKeyWord").is(searchResultInfo.getSearchKeyWord());
        query.addCriteria(criteria);

        SearchResultInfo one = mongoTemplate.findOne(query, SearchResultInfo.class);
        if (one != null) {
            one.setFg(0);
            log.info("数据修改成功,修改结果,{},fg = {}", one.getSearchKeyWord(), one.getFg());
            mongoTemplate.save(one);
        }

    }

    /**
     * 将查询结果保存到数据库
     *
     * @param keyWord
     * @param subKeyWord
     * @param x
     */
    private void saveSearchInfo(String keyWord, String subKeyWord, Object x) {

        final Query query = new Query();
        final Criteria criteria = Criteria.where("searchKeyWord").is(x);
        query.addCriteria(criteria);

        SearchResultInfo one = mongoTemplate.findOne(query, SearchResultInfo.class);
        if (one == null) {
            one = new SearchResultInfo();
            one.init();
        }
        one.setKeyWord(keyWord);
        one.setSubKeyWord(subKeyWord);
        one.setSearchKeyWord(x.toString());
        one.update();
        mongoTemplate.save(one);
    }

}
