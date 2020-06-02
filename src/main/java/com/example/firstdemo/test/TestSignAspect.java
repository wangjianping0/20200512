package com.example.firstdemo.test;

import com.example.firstdemo.util.JsonSerializer;
import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
@CommonsLog
public class TestSignAspect {
    @Pointcut("@annotation(TestSign)")
    public void testSignPointCut(){

    }
    @Around(value = "testSignPointCut()")
    public Object testSignAop(ProceedingJoinPoint joinPoint){
        Object [] objects=joinPoint.getArgs();
        System.out.println(JsonSerializer.serialize(objects));
        System.out.println("---------切面前-------------");
        Object object = null;
        try {
            object=joinPoint.proceed(objects);
            System.out.println(JsonSerializer.serialize(object));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("-----------切面后-----------");
        return  object;
    }
}
