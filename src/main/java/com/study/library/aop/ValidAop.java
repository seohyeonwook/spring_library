package com.study.library.aop;

import com.study.library.exception.ValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ValidAop {

    @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        BeanPropertyBindingResult bindingResult = null;

        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult =(BeanPropertyBindingResult) arg;
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
