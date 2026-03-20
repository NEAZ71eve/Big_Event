package com.demo.interceptors;

import com.demo.Utils.JWTUtil;
import com.demo.Utils.ThreadLocalUtil;
import com.demo.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTUtil jwt;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle) throws Exception{
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claim = jwt.parseToken(token);
            ThreadLocalUtil.set(claim);
            return true;
        }catch (Exception e){
            //还未实现令牌过期验证
            response.setStatus(401);
            return false;
        }

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handle,Exception ex) throws Exception{
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
