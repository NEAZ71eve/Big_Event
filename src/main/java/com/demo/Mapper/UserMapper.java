package com.demo.Mapper;

import com.demo.entity.User;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

/**
 * 用户Mapper接口（修正版）
 */
@Mapper
public interface UserMapper { // 注意：这里应该是 interface，不是 class！你原代码写的 class 是重大错误

    /**
     * 根据用户名查询是否存在
     * @param username 用户名
     * @return 存在返回1，不存在返回0
     */
    @Select("select count(1) from user where username = #{username}")
    boolean findByUserName(String username);

    /**
     * 新增用户
     * @param username 用户名
     * @param passcode 加密后的密码
     * @return 影响行数（1=成功，0=失败）
     */
    @Insert("insert into user(username,password,create_time,update_time) " +
            "values(#{username},#{passcode},now(),now())")
    void add(@Param("username") String username, @Param("passcode") String passcode);

    // 可选：如果需要查询完整用户信息，补充这个方法
    @Select("select * from user where username = #{username}")
    User findUserByUserName(String username);

    //更新用户信息
    @Update("update users set nickname = #{nickname} ,email = #{email},up_datetime = #{update_time} where id = #{id}")
    void update(@Param("nickname") String nickname, @Param("email") String email, @Param("update_time")LocalDateTime localDateTime,@Param("id") Integer id);

    //更换用户头像
    @Update("update user set user_pic = #{avatarUrl},update_time = now() where id = #{id}")
    void updateAvatar(@Param("avatarUrl")String avatarUrl,@Param("id")Integer id);

    //更新密码
    @Update("update user set password = #{newPassword},update_time = now() where id = #{id}")
    void updatePwd(@Pattern(regexp = "newPassword")String newPassword,@Pattern(regexp = "id") Integer id);
}