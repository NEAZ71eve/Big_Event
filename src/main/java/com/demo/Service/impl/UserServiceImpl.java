package com.demo.Service.impl;

import com.demo.Mapper.UserMapper;
import com.demo.Service.UserService;
import com.demo.entity.Result;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncode;
    @Override
    public boolean findByUserName(String username){
        return userMapper.findByUserName(username);
    }
    @Override
    public void register(String username,String password){
        // 加密
        String pacode = passwordEncode.encode(password);
        userMapper.add(username,pacode);
    }
    @Override
    public User findUserByUserName(String username){
        return userMapper.findUserByUserName(username);
    }
    @Override
    public void update(User user){
        userMapper.update(user.getNickname(),user.getEmail(),user.getUpdateTime(),user.getId());
    }
    @Override
    public void updateAvatar(String avatarUrl,Integer id){
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String new_password, Integer id) {
        userMapper.updatePwd(passwordEncode.encode(new_password),id);
    }
}
