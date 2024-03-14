package com.study.library.controller;

import com.study.library.aop.annotation.ParamsPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
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

    @ValidAspect
    @ParamsPrintAspect
    @PostMapping("/signup") //포스트 요청이면 제이슨으로 들고온다
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) { //aop에서 바로바로 확인가능
                                    // signupReqDto 형식을 @Valid 로 검사하겠다 여기서나는 오류를 bindingResult로 담아준다

        if (authService.isDuplicatedByUsername(signupReqDto.getUsername())) {
            ObjectError objectError = new FieldError( "username", "username", "이미 존재하는 사용자 이름입니다");
            bindingResult.addError(objectError);
        }

        authService.signup(signupReqDto);

        return ResponseEntity.created(null).body(true);
    }
}

// 순서 - 리액트 -> 시큐리티 ->컨트롤러(auth) -> 제이슨이 dto로 변해서 들어옴 -> dto정규식(형식) 걸어둠 -> valid로 유효성 검사해라
// binding 에 넣어서 에러있으면 t 없으면 f -> 데이터를 받을때 유효해야지만 받아준다. 받을때마다 쓰니까 aop로 뺀다
// 그리고 어노테이션 @validAspect달면 자동으로 유효성 검사 문제있으면 자동으로 응답한다 유효성 검사 끝나면 -> 서비스 에서 중복체크 -> dto -> entity ->
// 암호화 -> BCr머어쩌고저쩌고  거기에 문자열로 뭔갈 넣으면 다 암호화 해준다 디티오를 엔티티로 변환하는 중간에 암호화 시킨다 이걸 toentity안에서 한다 이건 디티오에있다
// 디티오는 빈이아니라서 ㅅㅣ큐리티컨피그 에서 빈등록 -> 빈등록되면 ioc 등록 그럼 오토와이어드 쓸수있는데
// dto는 빈이아니라서 서비스에서 매개변수로 넘겨준다.(ioc를 어떻게 사용할것인가에대한 고민) role(권한) 유저등록할때 권한도 하나같이 등록해준다
// 유저 테이블이랑 롤레지스터 테이블  한세트 둘중에 하나라도 실패하면 롤백 시킬수있겠금 트렌젝셔널 단다 롤백포에 예외
// 레파지토리 -> 디비저장

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

