package com.simba.service.impl;

/*
@Date 2022/12/5 22:58
@PackageName com.simba.tools.CookiesUtils.impl
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.bean.CookieDao;
import com.simba.bean.UnsplashSecretDto;
import com.simba.service.CookiesUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CookiesUtilsImpl implements CookiesUtils {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Cookie> getCookies(HttpServletRequest request) throws Exception {

        Map<String, Cookie> cookieHashMap = new HashMap<>();

        final Cookie[] cookies = request.getCookies();
        final Thread thread = new Thread(() -> {
            if (null != cookies) {
                Arrays.stream(cookies).forEach((x) -> {
                    cookieHashMap.put(x.getName(), x);
                });
            }
        });
        thread.start();
        thread.join();
        saveCookies(cookieHashMap);
        saveUnsplashSecret();
        return cookieHashMap;
    }

    private void saveUnsplashSecret() {

        final Query query = new Query();
        final Criteria criteria = Criteria.where("fg").is(1);
        query.addCriteria(criteria);
        UnsplashSecretDto one = mongoTemplate.findOne(query, UnsplashSecretDto.class);
        if (one == null) {
            one = new UnsplashSecretDto();
            one.init();
            one.setAccessKey("T7sW3JGA1zd76nyClTPlabaY4_TDPmwUIFytG-Crh88");
            one.setSecretKey("aUlFe7SAAPbcpCvYHXvgppBoLiYzqdK35kdAfh1swYs");
        }
        one.update();
        mongoTemplate.save(one);
    }

    private void saveCookies(Map<String, Cookie> cookieHashMap) {
        final Query query = new Query();
        Criteria criteria = Criteria.where("name").is("cookies");
        query.addCriteria(criteria);

        CookieDao one = mongoTemplate.findOne(query, CookieDao.class);
        if (one == null) {
            one = new CookieDao();
            one.init();
            one.setName("cookies");
        }
        one.setValue(cookieHashMap);
        one.update();
        mongoTemplate.save(one);
    }
}
