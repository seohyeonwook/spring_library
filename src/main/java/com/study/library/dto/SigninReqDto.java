package com.study.library.dto;

import lombok.Data;

@Data

 // 데이터 전송을 위한 객체를 정의하는 패키지입니다. 주로 클라이언트와 서버 간에 데이터를 주고받을 때 사용

//로그인 요청에 필요한 정보  데이터를 전달하기 위한 데이터 전송 객체(DTO)
public class SigninReqDto {
    private String username;
    private String password;
}
