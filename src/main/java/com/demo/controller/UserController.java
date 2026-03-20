package com.demo.controller;
import com.demo.Utils.JWTUtil;
import com.demo.Utils.ThreadLocalUtil;
import com.demo.entity.Result;
import com.demo.Service.UserService;

import com.demo.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$")String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查看已存在用户
        if (userService.findByUserName(username)){
            return Result.error("用户名已存在");
        }
        userService.register(username,password);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$")String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        // 检查用户是否存在
        User user = userService.findUserByUserName(username);
        if (user == null){
            return Result.error("用户名不存在");
        }
        // 检查加密后的密码是否相同
        if (!passwordEncoder.matches(password,user.getPassword())){
            return Result.error("密码错误");
        }
        // 都没问题后生成token
        JWTUtil jwt = new JWTUtil();
        Integer userid = user.getId();
        String token = jwt.generateToken(userid,username);
        return Result.success(token);
    }
    // 查询用户功能
    @GetMapping("/userInfo")
    public Result<User> userInfo (@RequestHeader(name = "Authorization")String token) {
        /*
        //根据用户名查找信息，从token中解析
        Map<String, Object> map = jwtUtil.parseToken(token);
        //将业务数据存到threadlocal

         */
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findUserByUserName(username);
        return Result.success(user);
    }
    //更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        try{
            userService.update(user);
        } catch (Exception e){
            return Result.error("更新失败");
        }
        return Result.success();
    }
    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
        try{
            Map<String,Object> map = ThreadLocalUtil.get();
            Integer id = (Integer) map.get("id");
            userService.updateAvatar(avatarUrl,id);
        } catch (Exception e){
            return Result.error("更新失败");
        }
        return Result.success();
    }
    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(Map<String,String> params){
        //1.校验参数
        String new_password = params.get("new_password");
        String old_password = params.get("old_password");
        String re_password = params.get("re_password");
        if(!StringUtils.hasLength(new_password) || !StringUtils.hasLength(old_password) || !StringUtils.hasLength(re_password)){
            return Result.error("缺少必要参数");
        }
        //2.检察院密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        User loginUser = userService.findUserByUserName((String) map.get("username"));
        String password = loginUser.getPassword();
        if (passwordEncoder.encode(old_password) == password){
            return Result.error("原密码不正确");
        }
        //3.new和re是否一样
        if (new_password == re_password){
            return Result.error("两次输入密码不一致");
        }
        //4.调用service层服务
        userService.updatePwd(new_password, loginUser.getId());
        return Result.success();
    }
}
