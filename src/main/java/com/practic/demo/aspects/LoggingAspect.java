package com.practic.demo.aspects;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.practic.demo.member.*Controller.*(..))")
    public void logBeforePointCut() {

    }

    @Around("logBeforePointCut()")
    public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("AOP Before");

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        ContentCachingRequestWrapper contentCachingRequestWrapper = (ContentCachingRequestWrapper) Objects.requireNonNull(
            attrs).getRequest();

        System.out.println(IOUtils.toString(contentCachingRequestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8.name()));

        Object result = joinPoint.proceed();
        return result;


    }


}
