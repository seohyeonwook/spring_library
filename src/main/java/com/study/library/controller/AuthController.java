package com.study.library.controller;

import com.study.library.aop.annotation.ParamsPrintAspect;
import com.study.library.dto.SignupReqDto;
import com.study.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Keymap;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")// 공통 상위 주소
public class AuthController { //0313 -2

    @Autowired
    private AuthService authService;

    @ParamsPrintAspect
    @PostMapping("/signup") //포스트 요청이면 제이슨으로 들고온다
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) { //aop에서 바로바로 확인가능
                                    // signupReqDto 형식을 @Valid 로 검사하겠다 여기서나는 오류를 bindingResult로 담아준다

        if (authService.isDuplicatedByUsername(signupReqDto.getUsername())) {
            ObjectError objectError = new FieldError( "username", "username", "이미 존재하는 사용자 이름입니다");
            bindingResult.addError(objectError);
        }

        if(bindingResult.hasErrors()) { //유효성 검사 //0314-1
            //error가 있으면
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            // getFieldErrors 처럼 s로끝나면 배열로 들고오거나 리스트로 들고온다고 추측해야함 마우스 올려보면 리스트 뜸
            // list<자료형>  변수명
            Map<String, String> errorMap = new HashMap<>();
            // error 들을 키밸류로 담기위해서 errorMap만든다
            for (FieldError fieldError : fieldErrors) {
                // ieldErrors의 요소를 fieldError에 할당 =>  fieldErrors에 들어있는 값을 담는 그릇이 fieldError
                String fieldName = fieldError.getField(); // dto변수명
                String message = fieldError.getDefaultMessage(); // 메세지내용
                errorMap.put(fieldName,message);
            }
            return ResponseEntity.badRequest().body(errorMap);
            // map사용법 알기***********************************************
        }

        authService.signup(signupReqDto);

        return ResponseEntity.created(null).body(true);
    }
}

// http - 1.url - 요청 메세지 (내가 무엇을 요청하는가) - 요청할때 쓴다 서버는 무조건 응답해준다
//        2.methed - 요청에 대한 구분 ,type //
//         메서드 종류    // get (r) - 주소창에 입력하면 무조건 get ex)localhost:3000/book = book이라는 페이지 보여달라는 요청
                         // post (c) - post요청을 날린다는건 추가하라는 뜻  ex) /user/vip 유저에 vip추가해라
                         // delete (d) -
                         // put (u)
// rest - csr에서만 사용가능
// 1. 메서드로 구분한다 = rest 일때만
// 2. 데이터는 무조건 명사  동작은 crud뿐이다
// 3. restapi에서 캐시 사용가능
// 4. 상태를 저장하지 않겠다 - 서버는 나를 기억하면 안돼 = 서버는 클라이언트에 대한 정보를 저장하지 않는다


// 요청하는 대상이 클라이언트

// url 둘로나뉨 1. 프론트 2. 백에서의 url
// 백 - rest (데이터 통신 (json))
// 프론트 -html (페이지 통신- 화면전환 눈에보이는것)
// http프로토콜은 둘다 사용하지만  rest 냐 html 이냐
/** 둘로 나뉘는건 csr - 프론트 랑 백
 *              ssr - html
 *              csr- 필요한 부품들만 받아와서 내가 만든다 html은 내가 가지고있다 데이터만 받는다
 *              ssr- 서버한테 완제품 만들어달라고 하는거  html통째로 받는거
 *              CSR은 브라우저에서 JavaScript를 사용하여 페이지를 생성하는 반면,
 *              SSR은 서버에서 페이지를 만들어 브라우저에 전송합니다.
 *              ssr - 스프링 부트에서 html 쓰면
 *              csr- 나눠쓰면 (리액트 스프링부트)
 *
 * tomcat = 웹서버 react = 프론트 서버
 *  나자신 = localhost 8080(톰캣) 3000(리액트)
 *
 *
 *
 */

