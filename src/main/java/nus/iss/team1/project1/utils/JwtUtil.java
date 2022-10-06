package nus.iss.team1.project1.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import nus.iss.team1.project1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class JwtUtil {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public static String key = "culverin";
    public static int exp_hours = 2;

//    private JwtUtil(RedisTemplate<Object, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }

    public String getToken(User user) {
        long now = System.currentTimeMillis();
        long exp = now+ 1000L *60*60*exp_hours;

        JwtBuilder jwtBuilder =  Jwts.builder()
                .setId(user.getId()+"")
                .setSubject(user.getUsername())//username
                .setIssuedAt(new Date())//login time
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(exp));

        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(user.getId()+"", user);
        redisTemplate.expire(user.getId()+"", 60L *60*exp_hours,  TimeUnit.SECONDS);

        return  jwtBuilder.compact();
    }

    public User validateToken(String token) {
        try{
            ValueOperations ops = redisTemplate.opsForValue();
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
                return null;
            }

            User user = (User)ops.get(claims.getId());
            if (!Objects.equals(user.getUsername(), claims.getSubject())){
                return null;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void removeToken(int userID) {
        redisTemplate.delete(userID+"");
    }
}