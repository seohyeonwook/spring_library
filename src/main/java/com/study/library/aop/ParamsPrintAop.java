package com.study.library.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // aop 디펜던시 넣어줘야함
@Component
public class ParamsPrintAop {//0313-4
    @Pointcut("@annotation(com.study.library.aop.annotation.ParamsPrintAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
        String className = codeSignature.getDeclaringTypeName();
        String methodName = codeSignature.getName();
        String[] argNames = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        for(int i = 0; i < argNames.length; i++) {
            log.info("{}: {} ({}.{})",argNames[i],args[i], className,methodName);
        }
        return proceedingJoinPoint.proceed();
    }

    @Aspect
    @Component
    public static class ValidAop {
        @Pointcut("@annotation(com.study.library.aop.annotation.ValidAspect)")
        private void pointCut() {}
    }
}
