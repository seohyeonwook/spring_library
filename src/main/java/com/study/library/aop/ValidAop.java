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

@Aspect
@Component
public class ValidAop {

    @Autowired
    private UserMapper userMapper;

    @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();

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
