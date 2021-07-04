package io.github.qzcsfchh.android.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoginAspect {

    @Pointcut("execution(@io.github.qzcsfchh.android.aspectj.annotation.CheckLoginState * *(..))")
    public void loginPC(){}

    @Around("loginPC()")
    public void checkLoginState(ProceedingJoinPoint pjp){

    }
}
