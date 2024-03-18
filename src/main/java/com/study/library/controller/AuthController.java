package com.study.library.controller;

import com.study.library.aop.annotation.ParamsPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.SigninReqDto;
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

// 클라이언트의 요청을 받아들이고, 그에 대한 응답을 반환하는 역할을 하는 웹 요청 처리 클래스가 포함

public class AuthController { //0313 -2

    @Autowired
    private AuthService authService;

    @ValidAspect
    @ParamsPrintAspect
    @PostMapping("/signup") //포스트 요청이면 제이슨으로 들고온다

    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) { //aop에서 바로바로 확인가능
                                    // signupReqDto 형식을 @Valid 로 검사하겠다 여기서나는 오류를 bindingResult로 담아준다

        authService.signup(signupReqDto);

        return ResponseEntity.created(null).body(true);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(authService.signin(signinReqDto));
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
 * 1. 폼에 있는것들 다 필여함 시큐리티 적용하는방법은 세션이랑 토큰 방식이있음
 * 2.  로그인 했을때  인증된사람이란걸 어떻게 판별할거냐??
 *      세션 - 로그인요청날렸을때 시큐리티가 아이디랑 패스워드 검증 검증이 되었을떄 세션 저장소에서 세션키값을 서버에 발급
 *            세션 키값을 응답해줌 그게 쿠키에 들어감  서버랑 쿠키에 둘다 키값이 있다. - 서버가 기억하고있다.
 *      토큰 - 정보를 다 가지고있는 클라이언트가  jwt 토큰안에 저장해서 yml 을 이용해서  요청을 날릴떄 분해해보라고 시킴
 *             그럼 서버에서 토큰발행을해줌
 *
 *  시큐리티에서 auth로 들어오는 요청 주소는 다 받아주라고 설정
 *  컨트롤러에서 auth위로 빼고 mapping  ,
 *  유효성 검사 -정보들이 데이터베이스에 들어가기 적합한가(검증) dto - 정규식 표현해서 형식을 정해줌
 *  valid 가 자동으로 검증해줌 bindingResult에 담긴다 그전에 aop동작함 around는 procddding getname하면 메소드이름이만들어짐
 *  아규스에 컨트롤러 매개변수 두개들고온다   bindingResult = been이랑 같다 반복돌려서 bindingResult에서 뺴준다 dto에서는 동작안함
 *  메소드 이름이 사인업이면  dto 반복문 돌려서 꺼낸다  널이아니다 = 찾았다 = 중복이다
 *
 *  중복이면 haserrors가 잡힌다 에러들을 꺼내서 맵에 담아서 예외발생시킨다 그럼 에러맵을 가지고 가서 validException 에서 잡아서
 *  배드로 응답해준다 예외 내용들이 응답해준다 -> 리액트로감?
 *
 *  정보가 잘 넘어오면 디티오에서 정보를 엔티티로 만들어준다  빈등록해서 ioc컨테이너에 등록된다
 *  그리고 동일한 빈인 서비스에서 매개변수로받고 유저객체 하나만든다 키프로퍼티에서 오토인크리먼트된 아이디 들고온다 권한을위해서
 *  그리고 User랑 role에서 예외터지면 무조건 롤백 하고 예외 날려 아니면 회원가입
 *  아무 오류없으면 컨트롤러에 리턴에서 true넣어서 응답해준다 그럼 정보랑 권한이 디비에 들어간다
 *  이정보를 가지고 로그인진행한다  그럼서비스에가서 해당 유저 정보를 찾는다 없으면 또 예외던진다
 *  그다음에 원본패스워드랑 암호화된것 두개를 같은지 비교해라 matches  틀렸으면 또 예외날리기
 *  둘다  맞추면 jwt에 유저 정보를 넘겨준다 jwt에서 유저정보 받고 jwtstring을 만들어준다 암호토큰만들어서 리턴해줌
 *  jwt에 토큰에 필요한거 아이디 이름 권한 만료시간 그리고 claim은 put이라고 생각해 그리고 암호화 시킨다 야물에 저장해둔 거 가져와서
 *  아무 이상없으면 또 컨트롤러
 *
 */

