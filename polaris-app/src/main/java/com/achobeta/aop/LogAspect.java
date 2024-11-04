package com.achobeta.aop;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */

import cn.hutool.core.util.IdUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * 日志拦截器
 */
@Component
//@Aspect
public class LogAspect {

    public static final String TRACE_ID = "traceId";

    /**
     * 拦截入口
     */
    @Pointcut("")
    public void pointCut() {
    }

    /**
     * 拦截处理
     * @param point point 信息
     * @return result
     * @throws Throwable if any
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        try {
            //添加 TRACE_ID
            MDC.put(TRACE_ID, IdUtil.objectId());
            return point.proceed();
        }finally {
            //移除 TRACE_ID
            MDC.remove(TRACE_ID);
        }
    }
}
