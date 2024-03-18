package com.study.library.controller.advice;

import com.study.library.exception.SaveException;
import com.study.library.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestControllerAdvice
// 관점 지향 프로그래밍에서 실행 시점에 코드를 주입하여 처리하는 클래스가 위치합니다.
// 예외 처리를 담당하는 클래스
public class ExceptionControllerAdvice {
    // 어노테이션 = 특성 추가 하기 속성부여

    // 엘리멘츠 - css 디자인
    // console - console창 찍어보기
    // network - 요청 날린 정보
    // 인터페이스 - 가이드 안에 들어있는 형식대로만 사용가능
    // 어노테이션 - 기능

    @ExceptionHandler(SaveException.class) // 처리하고자 하는 예외 클래스 지정
    public ResponseEntity<?> saveException(SaveException e) {
        return ResponseEntity.internalServerError().body(e.getMessage()); //500에러 코드
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException e) {
        return ResponseEntity.badRequest().body(e.getErrorMap());
    }

    @ExceptionHandler(UsernameNotFoundException.class)// 자동 으로 UsernameNotFoundException 생성해서 내장 되어있는거 사용
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
