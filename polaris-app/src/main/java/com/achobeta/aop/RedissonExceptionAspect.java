package com.achobeta.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yangzhiyao
 * @description redisson异常切面
 * @date 2024/11/30
 */
@Aspect
@Component
@Slf4j
public class RedissonExceptionAspect {

    @Around("execution(* com.achobeta.infrastructure.redis.IRedisService.*(..))")
    public Object handleRedissonService(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("RedissonService异常", e);
            return null;
        }
    }
}
