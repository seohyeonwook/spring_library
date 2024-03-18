package com.study.library.security.filter;

import com.study.library.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    // 서블릿 필터를 담당하는 클래스가 위치합니다. 클라이언트의 요청을 가로채어 특정 작업을 수행합니다.
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean isPermitAll =( Boolean ) request.getAttribute("isPermitAll");

        if(!isPermitAll) {// 인증이 필요없으면 그냥 다음 필터로 넘어감
            String accessToken = request.getHeader("Authorization");
            String removedBearerToken = jwtProvider.removeBearer(accessToken);
            Claims claims = jwtProvider.getClaims(removedBearerToken);// 요청 유알엘에 따라서 이거 실행할건지 말건지
            if(claims == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());//401 에러 -인증실패
                return;
            }
            Authentication authentication = jwtProvider.getAuthentication(claims);

            if(authentication == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());//401 에러 -인증실패
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        //전처리
        filterChain.doFilter(request,response);
        //후처리
    }


}
// 회원가입과 로그인은 필터가 필요없다 필터 사용이유= 토큰이 만들어진 이후에  이 토큰으로 인증하는거임
/* 로그인은 토큰을 생성하거나 발급하기 위한 동작이지 인증이아니다
   * 토큰을 발급을 받아서 가지고 있으면 토큰으로 어딘가에 들어갈때 보안(필터)를 거쳐서 들어갈수있게끔 하는 동장
   * 토큰 인증이 필요한 요청인지 필요없는 요청인지 config에서 필터가 끝나야 컨트롤러로 간다
* */

