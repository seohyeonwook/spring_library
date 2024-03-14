package com.study.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 크로스 오리진 허용 3000 / 8080
                                                         // CORS(Cross-Origin Resource Sharing) 구성 메서드
        registry.addMapping("/**")    // 모든 요청 주소
                .allowedHeaders("*")  // 모든메서드
                .allowedOrigins("*"); // 어디에서 들어오든  다받아준다
    }
}
// 크로스 오리진(Cross-Origin)은 보안 상의 이유로 인해 브라우저에서 다른 출처(origin)로부터
// 리소스에 접근하는 것을 제한하는 정책을 의미합니다.
// 출처(origin)란 프로토콜(예: http, https), 호스트(도메인), 포트 번호의 조합을 말합니다.
