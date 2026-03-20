package com.demo.Utils;

import com.demo.entity.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


// 生成密钥，生成token，验证token，解析token
@Component
public class JWTUtil {
    //定义密钥
    @Value("${jwt.secret:aljdlencuebalsiwnfizleifneialeiri")
    private String secret;

    //定义token过期时间
    @Value("${jwt.expire:70000}")
    private long expire;

    //生成token
    public String generateToken(Integer userid,String username){
        //生成密钥
        SecretKey Key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                    //荷载，用户id,用户名
                .claim("userId",userid)
                .claim("userName",username)
                    //签发时间，过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                    //头加密钥
                .signWith(Key)
                .compact();
    }
    // 验证Token是否有效（过期/签名错误都会抛异常）
    public Result<String> validateToken(String token) {
        try {
            parseToken(token);
            return Result.success("文章数据");
        } catch (Exception e) {
            return Result.error("未登录");
        }
    }

    // 解析Token，获取载荷信息
    public Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 从Token中获取用户名
    public String getUsernameFromToken(String token) {
        return parseToken(token).get("username", String.class);
    }

    // 从Token中获取用户ID
    public Integer getUserIdFromToken(String token) {
        return parseToken(token).get("userId", Integer.class);
    }
}


