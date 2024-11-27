package com.achobeta.aop;

import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 严豪哲
 * @Description: 访问个人私有资源权限拦截器
 * @Date: 2024/11/27 21:40
 * @Version: 1.0
 */

@Slf4j
@Component
@Aspect
@Order(Integer.MIN_VALUE+1)
public class SelfPermissionVerificationAspect {

    private final String TOKENINFO = "tokenInfo";

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.achobeta.types.constraint.SelfPermissionVerification)")
    public void pointCut(){
    }

    /**
     * 拦截处理
     * @param joinPoint joinPoint 信息
     * @return result
     * @throws Throwable if any
     */
    @Around("pointCut()")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取当前请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取token信息
        TokenVO tokenVO = (TokenVO) request.getAttribute(TOKENINFO);

        //正常不会进到这 因为登陆校验在本校验之前
        if(tokenVO == null || tokenVO.getUserId() == null){
            log.info("登陆校验未通过,tokenInfo为空,无法获取userId");
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getCode()), GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getMessage());
        }

        //这里如果再从redis里面获取token信息,token可能过期失效,所以这里不获取用登录校验处传来的
        String tokenUserId = String.valueOf(tokenVO.getUserId());

        // 获取用户ID
        Object arg = joinPoint.getArgs()[0];
        String targetUserId = (String) arg.getClass().getMethod("getUserId").invoke(arg);

        // 校验用户ID是否相同
        if (tokenUserId.equals(targetUserId)) {
            log.info("当前用户访问的是个人私有资源,用户id相同,可以放行,userId:{}",tokenUserId);
            return joinPoint.proceed();
        } else {
            log.info("当前用户访问的是个人私有资源,用户id不相同,不可以放行,userId:{}",tokenUserId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_NO_PERMISSION.getCode()), GlobalServiceStatusCode.USER_NO_PERMISSION.getMessage());
        }

    }
}
