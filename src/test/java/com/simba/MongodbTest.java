package com.simba;

/*
@Date 2022/12/5 0:23
@PackageName com.simba
@Author liguangxin
@Descrption MongoTemplate测试；
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import com.simba.bean.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MongodbTest {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void saveData() {

        final Query query = new Query();
        final Criteria criteria = Criteria.where("fg").is(1)
                .andOperator(Criteria.where("bookId").is(10086));
        query.addCriteria(criteria);
        Book x = mongoTemplate.findOne(query, Book.class);

        //判断如果不存在就初始化，否则，直接更新；
        if (x == null) {
            x = new Book();
            x.init();
            log.info("init success");
        }

        x.setBookId(10086);
        x.setName("simbasimba");
        x.setType("book");
        x.setDesc("love123");
        x.update();
        mongoTemplate.save(x);
        log.info("save success");
        Query q = new Query();
        q.addCriteria(Criteria.where("fg").is(1));
        long count = mongoTemplate.count(q, Book.class);
        log.info("当前有效数据,{}",count);

    }

    @Test
    public void dropData() {
        mongoTemplate.dropCollection("book");
        final Query query = new Query();
        final Criteria criteria = Criteria.where("fg").is(1);
        query.addCriteria(criteria);
        long count = mongoTemplate.count(query, Book.class);
        if (count == 0) {
            log.info("数据清除成功");
        }
    }

    @Test
    public void updateData() {
        final Query query = new Query();
        final Criteria criteria = Criteria.where("fg").is(1)
                .andOperator(Criteria.where("bookId").is(10086));
        query.addCriteria(criteria);
        Book x = mongoTemplate.findOne(query, Book.class);
        if (x != null) {
            x.setFg(0);
            x.update();
            mongoTemplate.save(x);
            log.info("update success");
        } else {
            log.info("sorry, data is not exist");
        }
    }
}
