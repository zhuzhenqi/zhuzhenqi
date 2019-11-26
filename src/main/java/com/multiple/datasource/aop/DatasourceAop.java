package com.multiple.datasource.aop;

import com.multiple.datasource.datasource.DBContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author zhuzhenqi
 * @date 2019/11/25 17:55
 */
@Component
@Aspect
public class DatasourceAop implements Ordered {
    @Pointcut("@annotation(com.multiple.datasource.annotation.Slave)")
    public void readPointcut() {
    }

    @Pointcut("@annotation(com.multiple.datasource.annotation.Master)")
    public void writePointcut() {
    }

    @Around("readPointcut()")
    public Object readPointcut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            DBContextHolder.slave();
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DBContextHolder.clearDB();
        }
    }

    @Around("writePointcut()")
    public Object writePointcut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            DBContextHolder.master();
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DBContextHolder.clearDB();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
