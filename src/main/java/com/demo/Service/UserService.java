package com.demo.Service;

import com.demo.entity.User;

import java.time.LocalDateTime;

public interface UserService {
    boolean findByUserName(String username);

    void register(String username, String password);

    User findUserByUserName(String username);
    void update(User user);

    void updateAvatar(String avatarUrl,Integer id);
}
