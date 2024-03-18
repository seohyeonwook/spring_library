package com.study.library.aop;

import com.study.library.dto.SignupReqDto;
import com.study.library.exception.ValidException;
import com.study.library.repository.UserMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect //이 클래스가 스프링 AOP에서 Aspect를 정의하는 클래스임을 나타냅니다.
@Component //  스프링 컨테이너에 이 클래스를 빈으로 등록하라는 것을 나타냅니다.
            // 이 클래스를 다른 빈에서 주입받아 사용할 수 있게 됩니다.
public class ValidAop { // Valid = 유효한
    //유효성 검사 수행

    @Autowired
    private UserMapper userMapper;
    // 클래스를 주입받기 위한 필드입니다. 이 필드를 통해 데이터베이스와 상호작용할 수 있습니다.

    @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        // ProceedingJoinPoint = 인터페이스 = 메소드 실행 전이나 후에 추가적인 동작을 수행할 때 사용
        // getName 현재 실행 중인 메소드의 이름을 가져옵니다.
        Object[] args = proceedingJoinPoint.getArgs();

        BeanPropertyBindingResult bindingResult = null;

        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult =(BeanPropertyBindingResult) arg;
            }
        }

        if(methodName.equals("signup")) {
            SignupReqDto signupReqDto = null;

            for(Object arg : args) {
                if(arg.getClass() == SignupReqDto.class) {
                    signupReqDto =(SignupReqDto) arg;
                }
            }
            if(userMapper.findUserByUsername(signupReqDto.getUsername()) != null) {
                ObjectError objectError = new FieldError("username", "username", "이미 존재하는 사용자이름입니다");
                bindingResult.addError(objectError);
            }
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

            throw new ValidException(errorMap);
            // map사용법 알기***********************************************
        }
        return proceedingJoinPoint.proceed();
    }

}
