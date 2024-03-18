package com.study.library.dto;

import com.study.library.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupReqDto {//0313-3

    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "영문자, 숫자 5 ~ 10자리 형식이어야 합니다")//정규식 들어가는 자리 = a-z 0-9  3자-10자
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,128}$",message = "영문자, 숫자, 특수문자를 포함한 5  5 ~ 128자리 형식이어야 합니다")
    private String password; //리액트에 password 체크는 디비까지 들고올 필요없다 거기서 바로 확인하자
    @Pattern(regexp = "^[가-힇]{1,}$",message = "한글문자 형식이어야 합니다")
    private String name;
    @Email(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{1,3}$",message = "이메일 형식이어야합니다") //0314-2
    private String email;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .build();
    }

    // CryptPasswordEncoder는 스프링 시큐리티에서 제공하는 클래스로, 안전한 방식으로 비밀번호를 암호화하는 데 사용됩니다.
    // encode() 메서드는 비밀번호를 암호화하는 데 사용되며, 이 메서드를 호출하여 사용자의 비밀번호를 암호화
}
