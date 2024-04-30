package com.xiaodeng.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author XiaoDeng
 * @Date 2023/8/22 08:29
 **/
@Aspect
@Component
public class LoggerAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggerAspect.class);


    @Before("execution(public * com.*.controller..*(..))")
    public void before(JoinPoint joinPoint) {
        String uuid = UUID.randomUUID().toString();
        MDC.put("requestId", uuid);
        log.info("Method {} is about to be executed , param {},", joinPoint.getSignature().toShortString(), Arrays.toString(joinPoint.getArgs()));
    }

    @Around("execution(public * com.*.controller..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("Method {} executed in {} ms.", joinPoint.getSignature().toShortString(), executionTime);
        MDC.remove("requestId");
        return result;
    }

}
