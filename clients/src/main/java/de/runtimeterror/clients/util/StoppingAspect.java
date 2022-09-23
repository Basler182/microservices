package de.runtimeterror.clients.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class StoppingAspect {

    @Around("@annotation(Stopped)")
    void measurementMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        try{
            stopWatch.start(joinPoint.getSignature().getName());
            joinPoint.proceed();
        } finally{
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        }
    }
}
