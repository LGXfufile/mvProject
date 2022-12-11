package com.simba;

/*
@Date 2022/6/1 23:14
@PackageName com.simba
@User liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.houbb.word.cloud.util.WordCloudHelper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import com.simba.bean.KeyWordInfo;
import com.simba.bean.SearchResultInfo;
import com.simba.tools.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.EditDistanceSimilarity;
import org.xm.similarity.text.TextSimilarity;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://blog.csdn.net/qq_29654777/article/details/104051567
 * http://wjhsh.net/sunsky303-p-11045113.html
 * https://blog.tianchenw.com/2445.html
 */
@SpringBootTest
@Slf4j
public class FirstTest {

    // 日志打印
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstTest.class);

    // 初始化操作
    private static JiebaSegmenter segmenter = new JiebaSegmenter();

    @Resource
    private MongoTemplate mongoTemplate;


    public void getResult() throws Exception {
        String url = "https://mmzztt.com/photo/tag/meitui/";
        URL targetUrl = new URL(url);

        Document parse = Jsoup.parse(targetUrl, 10000);
        Elements elementsByClass = parse.getElementsByClass("uk-grid-margin");
        System.out.println(elementsByClass);
    }


    public void aa() throws IOException {
        String randomId = CommonUtils.getRandomId();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/txfFile/" + randomId + ".txt"));
        String returnResult = "123";
        bufferedWriter.write(returnResult);
        System.out.println("end");
        bufferedWriter.close();
    }

    @Test
    public void lianxiang() throws IOException {
        String keyWord = "QQ小世界怎么查看".trim();
        StringBuffer buffer = new StringBuffer();

        String arr[] = {"", "怎么", "如何", "哪里", "能", "快速", "怎么样",
                "是真的吗", "能不能", "是不是", "可不可以", "需不需要"};
        String subKeyWord;
        AtomicInteger count = new AtomicInteger();
        final long start = new Date().getTime();

        for (String s : arr) {
            for (int i = 0; i < 26; i++) {
                char c = (char) ('a' + i);
                subKeyWord = keyWord + s + c;
                String url = "https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd=" + subKeyWord.trim() + "&json=1";
                final Document parse = Jsoup.parse(new URL(url), 30000);
                String text = parse.text();
                String vv = "window.baidu.sug(";
                final String substring = text.substring(vv.length(), text.length() - 2);
                final JSONObject jsonObject = JSONUtil.parseObj(substring);
                final Object s1 = jsonObject.get("s");
                final JSONArray objects = JSONUtil.parseArray(s1);
                String finalSubKeyWord = subKeyWord;
                objects.stream().forEach(x -> {
                    System.out.println(x);
                    buffer.append(x);
                    buffer.append(",");
                    saveSearchInfo(keyWord, finalSubKeyWord, x);
                    saveKeyWord(keyWord);
                    count.getAndIncrement();
                });
            }
        }
        final long end = new Date().getTime();
        long ss = (end - start) / 1000;
        log.info("写入数据条数:{}", count);
        log.info("用时,{}秒", ss);
//        WordCloudHelper.wordCloud(buffer.toString(),"src/main/resources/file/3.png","src/main/resources/file/2.png");

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

    @Test
    public void ee() {
        String text1 = "soul如何取消别人的关注";
        String text2 = "soul如何取消掉自动续费";
        TextSimilarity cosSimilarity = new CosineSimilarity();
        double score1 = cosSimilarity.getSimilarity(text1, text2);
        System.out.println("cos相似度分值：" + score1);

        TextSimilarity editSimilarity = new EditDistanceSimilarity();
        double score2 = editSimilarity.getSimilarity(text1, text2);
        System.out.println("edit相似度分值：" + score2);
    }

    @Test
    public void ff() {
        WordCloudHelper.wordCloud("爱你爱你很爱你", "src/main/resources/file/1.png");
    }

    @Test
    public void dadasd() throws IOException {
        String url = "http://www.baidu.com/s?wd=怎么快速";
        final Document parse = Jsoup.parse(new URL(url), 30000);

        String text = parse.text();
        System.out.println(text);

    }

    @Test
    public void getSignaleWord() {

        String basePath = "E:\\javaLearn\\JavaLearning\\simba\\src\\main\\resources\\data";

        // 匹配分词模板
        Path path = FileSystems.getDefault().getPath(basePath + "/files", "dic.txt");
        System.out.println(path);
        WordDictionary.getInstance().loadUserDict(path);

        System.out.println(segmenter.sentenceProcess("小明硕士毕业于中国科学院计算所，后在日本京都大学深造"));
    }

    @Test
    public void getAll() {

        String keyWord = "抖音";
        final Query query = new Query();
        final Criteria criteria = Criteria.where("keyWord").is(keyWord);
        query.addCriteria(criteria);

        HashMap<String, Integer> map = new HashMap<>();
        final List<SearchResultInfo> searchResultInfos = mongoTemplate.find(query, SearchResultInfo.class);
        searchResultInfos.stream().forEach(x -> {
            final List<String> strings = segmenter.sentenceProcess(x.getSearchKeyWord());
            strings.stream().forEach(y -> {
                if (!map.containsKey(y)) {
                    map.put(y, 1);
                } else {
                    map.put(y, map.get(y) + 1);
                }
            });
        });

        final Set<String> set = map.keySet();
        int max = 1;
        List<String> list = new ArrayList<>();
        list.add("抖音");
        list.add("怎么");
        list.add("如何");
        list.add("能");
        list.add("吗");
        list.add("哪里");
        list.add("快速");
        list.add("的");
        list.add("视频");
        list.add("别人");
        list.add("直播");
        list.add("作品");
        list.add("认证");
        list.add("设置");
        list.add("看");
        list.add("是");
        list.add("粉丝");
        list.add("自己");
        list.add("看到");
        list.add("什么");
        list.add("取消");
        list.add("在线");
        list.add("评论");
        list.add("记录");
        list.add("好友");
        list.add("下载");
        list.add("开通");
        list.add("赚钱");

        for (String s : set) {
            if (list.contains(s)) {
                continue;
            }

            if (map.get(s) > max) {
                max = map.get(s);
            }
        }

        for (String s : set) {

            if (map.get(s) == max) {
                System.out.println(s + "::" + map.get(s));

            }
        }


//        mongoTemplate.save()
        final JSONObject jsonObject = JSONUtil.parseObj(map);
        System.out.println(jsonObject);

    }

    @Test
    public void tiku() throws Exception {
        String url = "http://cooco.net.cn/";
        final Document parse = Jsoup.parse(new URL(url), 5000);
        System.out.println(parse.text());
    }

    @Test
    public void zhuancun() {

        final Query query = new Query();
        final Criteria criteria = Criteria.where("fg").is(1);
        query.addCriteria(criteria);
        List<SearchResultInfo> searchResultInfos = mongoTemplate.find(query, SearchResultInfo.class);
        searchResultInfos.stream().forEach(x->{
            String keyWord = x.getKeyWord();
            saveKeyWord(keyWord);
        });
    }

}
