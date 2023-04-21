package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user_account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Select("select * from user_account where username = #{text} or email = #{text}")
    UserInfo findUserInfoByNameOrEmail(String text);

    @Insert("insert into user_account (username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(String username, String password, String email);

    @Update("update user_account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String email, String password);
}
