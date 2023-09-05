package com.example.KafkaCamundaTest.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ActivityLoggingAspect {

    @Before("execution(* com.example.KafkaCamundaTest.task.*.*.execute(..))")
    public void logStart(JoinPoint joinPoint) {
        var targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Executing {} activity", targetClassName);
    }

    @After("execution(* com.example.KafkaCamundaTest.task.*.*.execute(..))")
    @SneakyThrows
    public void logEnd(JoinPoint joinPoint) {
        var targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Finishing {} activity", targetClassName);
    }

}
