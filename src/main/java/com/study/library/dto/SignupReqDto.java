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





    @Pattern(regexp = "^[a-z]{1}[a-z0-9]{2,10}+$", message = "영문 숫자 조합 3~10자리")//정규식 들어가는 자리 = a-z 0-9  3자-10자
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{7,128}+$",message = "대소문자, 숫자, 특수문자 조합으로 8 ~ 128자리")
    private String password; //리액트에 password 체크는 디비까지 들고올 필요없다 거기서 바로 확인하자
    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣]*$",message = "한글이어야 합니다")
    private String name;
    @Email(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$") //0314-2
    private String email;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .build();
    }
}
