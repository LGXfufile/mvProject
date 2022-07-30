package com.simba.mapper;



import com.simba.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper//表示是mybatis的mapper类,这是Dao层；
@Repository // 这里也可以用@Component,只是为了分层；
public interface UserMapper {

    List<User> queryUserAll();


    List<User> queryUserById(User user);

    boolean addUser(User user);

}
