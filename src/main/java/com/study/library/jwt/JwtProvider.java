package com.study.library.jwt;

import com.study.library.entity.User;
import com.study.library.security.PrincipalUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }



    public String generateToken(User user) {


        int userId = user.getUserId();
        String username = user.getUsername();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        Date expireDate = new Date(new Date().getTime() +( 1000 * 60 * 60 * 24));
        //    만료날짜               지금 시간에서 하루 더해라  => 하루가더해진 시간 객체가 만들어짐
        String accessToken = Jwts.builder()
                .claim("userId", userId) //제이슨 형식으로 키밸류 들어감
                .claim("username", username) //claim 커스텀된(우리가정해준) 키값
                .claim("authorities", authorities)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)//암호화 키값,알고리즘
                .compact();

        return accessToken;
    }

}
