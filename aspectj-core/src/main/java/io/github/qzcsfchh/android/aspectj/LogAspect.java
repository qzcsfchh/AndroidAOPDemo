package io.github.qzcsfchh.android.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class LogAspect {

    /**
     * 不能切，因为不是.java
     */
    @Pointcut("execution(void *..AppCompatActivity.on*(..))")
    public void activityLifecycle(){}

    /**
     * 可以切，因为是.java文件
     */
    @Pointcut("execution(void *..BaseFragment.on*(..))")
    public void fragmentLifecycle(){}

    @Before("activityLifecycle()")
    public void action_log_activityLifecycle(JoinPoint joinPoint){
//        String s = joinPoint.getTarget().toString();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    args[i] = args[i].getClass().getSimpleName();
                }
            }
        }
        Log.d(declaringTypeName, name+": "+ Arrays.toString(args));
    }


    @Before("fragmentLifecycle()")
    public void action_log_fragmentLifecycle(JoinPoint joinPoint){
//        String s = joinPoint.getTarget().toString();
        Signature signature = joinPoint.getSignature();
        String simpleName = signature.getDeclaringType().getSimpleName();
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getName();
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    args[i] = args[i].getClass().getSimpleName();
                }
            }
        }
        Log.d(simpleName, name+": "+ Arrays.toString(args));
    }
}
