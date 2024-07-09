package com.aop.demo.aspect;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.aop.demo.controller.*.*(..))")
    public void loggingPointCut() {
    }

    @Around("loggingPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method invoked: " + joinPoint.getSignature());
        logger.info("Request: " + Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        logger.info("After method invoked: " + joinPoint.getSignature());
        logger.info("Response: " + result);

        return result;
    }

    @AfterThrowing(pointcut = "loggingPointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.info("After method invoked: " + joinPoint.getSignature());
        logger.info("Method threw exception: " + exception.getMessage());
    }
}
