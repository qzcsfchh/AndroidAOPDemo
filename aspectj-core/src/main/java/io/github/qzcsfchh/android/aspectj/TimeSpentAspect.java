package io.github.qzcsfchh.android.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TimeSpentAspect {

    @Pointcut("execution(@io.github.qzcsfchh.android.aspectj.annotation.CheckTimeSpent * *(..))")
    public void timeSpentMethod(){}
    @Pointcut("execution(@io.github.qzcsfchh.android.aspectj.annotation.CheckTimeSpent *.new(..))")
    public void timeSpentConstructor(){}


    @Around("timeSpentMethod() || timeSpentConstructor()")
    public Object insertTimeSpent(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        StringBuilder sb = new StringBuilder(signature.getName()).append("(");
        if (args!=null) {
            for (Object arg : args) {
                if (arg instanceof String) {
                    sb.append((String) arg);
                } else if (arg instanceof Class) {
                    sb.append(((Class) arg).getSimpleName());
                }
            }
        }
        sb.append(")").append(" took: ").append((System.currentTimeMillis()-start)).append("ms");
        Log.w(signature.getDeclaringType().getSimpleName(), sb.toString());
        return proceed;
    }
}
