package com.achobeta.aop;

import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.TokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/17 15:26
 * @Version: 1.0
 */

@Component
@Aspect
public class CheckRTAspect {

    @Resource
    private ITokenRepository tokenRepository;

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.achobeta.types.constraint.CheckRT)")
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
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String && ((String) args[i]).length()>100) {
                String token = (String) args[0];
                if (checkRT(token)&&checkRTFromRedis(token)) {
                    return joinPoint.proceed();
                }
            }
        }
        throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
    }

    private Boolean checkRT(String token) {

        String type = TokenUtil.getTypeByReflashToken(token);
        Boolean autoLogin;

        //根据token类型判断是否自动登录
        if(type == null) {
            return false;
        }
        else if (type.equals(TokenUtil.REFRESH_TOKEN_TYPE[0])) {
            autoLogin = false;
        }
        else if (type.equals(TokenUtil.REFRESH_TOKEN_TYPE[1])) {
            autoLogin = true;
        }
        else {
            //token类型不合法
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
        }

        //用tokenUtil检查token是否合法
        if(TokenUtil.checkReflashToken(token, autoLogin)){
            return true;
        }
        else {
            return false;
        }

    }

    private Boolean checkRTFromRedis(String token) {
        return tokenRepository.checkToken(token);
    }
}
