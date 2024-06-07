package com.gfk.framework.aspect.joblog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class JobLogAspect {
    @Around(value = "@annotation(jobLog)")
    public Object aroundJob(ProceedingJoinPoint point, JobLog jobLog) throws Throwable{
        log.info("JobLog - " + jobLog.value());
        return point.proceed();
    }
}
