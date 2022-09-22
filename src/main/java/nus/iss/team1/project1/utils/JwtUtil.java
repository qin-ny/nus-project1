package nus.iss.team1.project1.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import nus.iss.team1.project1.models.User;
/**
 */
public class JwtUtil {
    public static String  getoken(User user) {
        //Jwts.builder()
        //Jwts.parser()
        JwtBuilder jwtBuilder =  Jwts.builder()
                .setId(user.getId()+"")
                .setSubject(user.getUsername())    //username
                .setIssuedAt(new Date())//login time
                .signWith(SignatureAlgorithm.HS256, "my-123").setExpiration(new Date(new
                        Date().getTime()+86400000));
        //set expire time
        //first three are playload, the last one is header
        System.out.println(jwtBuilder.compact());
        return  jwtBuilder.compact();
    }
}