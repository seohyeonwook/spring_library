package com.study.library.exception;

import lombok.Getter;

import java.util.Map;

public class ValidException extends RuntimeException {
    // aop 에서 유효성 검사 오류 예외
    @Getter
    Map<String, String> errorMap;
    // 아래에서 만들어진걸 가져오기 위해서 = 정의 부분 int a 로만들어두고 a에 아래의 값을 받는 형식

    public ValidException( Map<String, String> errorMap ) {// 자료형 + 변수명
        super("유효성 검사 오류");
        this.errorMap = errorMap;
    }
}
