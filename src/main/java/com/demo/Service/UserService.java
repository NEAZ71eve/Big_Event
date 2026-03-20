package com.demo.Service;

import com.demo.entity.Result;
import com.demo.entity.User;

import java.time.LocalDateTime;
import java.util.Map;

public interface UserService {
    boolean findByUserName(String username);

    void register(String username, String password);

    User findUserByUserName(String username);
    void update(User user);

    void updateAvatar(String avatarUrl,Integer id);

    void updatePwd(String newPassword,Integer id);
}
