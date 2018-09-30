package com.zjx.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.Date;

/**
 * <p>@ClassName: TimeAspect </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/27 20:09</p>
 */
//@Aspect
//@Component
public class TimeAspect {
    @Around("execution(* com.zjx.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{

        System.out.println("time aspect start");
        long start = new Date().getTime();

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is:" + arg);
        }
        Object object = pjp.proceed();
        System.out.println("time filter 耗时:" + (new Date().getTime() - start));
        System.out.println("time aspect end");
        return object;
    }
}
