package com.study.library.jwt;

import com.study.library.entity.User;
import com.study.library.repository.UserMapper;
import com.study.library.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
// JSON Web Token(JWT) 생성과 검증을 다루는 클래스가 위치합니다. 클라이언트의 인증을 담당합니다.
public class JwtProvider {

    private final Key key;
    private UserMapper userMapper;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,// 야믈에 있는거 가지고와서
            @Autowired UserMapper userMapper) {// 매개변수에 Autowired달수있다
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.userMapper = userMapper;
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

    public String removeBearer(String token) {
        if(!StringUtils.hasText(token)) { //문자열인지 검증해주는
            return null;
        }
        return token.substring("Bearer ".length());//문자열 잘라서 가지고오는거  bearer 자르기위해서
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)// 토큰을 클래임으로 변환하는 작업
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 인증 오류: {}", e.getMessage());
        }

        return claims;
    }

    public Authentication getAuthentication(Claims claims) {
        String username = claims.get("username").toString();
        User user = userMapper.findUserByUsername(username);
        if(user == null) {
            // 토큰은 유효하지망 db에서 user정보다 삭제되었을 경우
            return null;
        }
        PrincipalUser principalUser = user.toPrincipalUser();
        return new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
    }

}
