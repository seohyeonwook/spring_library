package com.study.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity // 기존시큐리티 말고 밑에 빌더 세팅해논거 따라가라
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {//0313-1
// 시큐리티  이미 완성 되어있는데 우리한테 맞게끔 상속해서 오버라이드 해서 (c + o) thhpsecurity 가져와야함
    // 보안설정 구성하는 클래쑤

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//csrf 검사하지마  //서버사이드랜더링/ 클라이언트 알아보기

        http.authorizeRequests() //빌더 패턴으러 - 요청이들어오면 아래 절차를 거쳐라 // . . . 찍는건 빌더패턴
                .antMatchers("/server/**", "/auth/**")// "/server/**" 서버라는 요청주소에 뒤에 머가 들어오든
                // (여기에 들어오면 인증이 필요없다 = 얘들은 허용해줘라)
                .permitAll()  //permit(권한)허용해라
                .anyRequest() // 나머지 모든요청은
                .authenticated(); //인증받아라

    }

}

// 시큐리티 - 필터 덩어리 자료가 다들어가면 안된다 들어가기 전에 걸러줘야지
