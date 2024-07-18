package com.xhxy.Mapper;

import com.xhxy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findUserByCredentials(@Param("number") Long number, @Param("username") String username, @Param("password") String password);
    User findUserById(@Param("id") Long id);

}
