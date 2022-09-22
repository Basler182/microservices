package de.runtimeterror.clients.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
class LoggingAspect {

    @Around("@annotation(Logged)")
    void logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var output = joinPoint.proceed();
        log.info("Method {} called with arguments {} and returned {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), output);
    }
}
