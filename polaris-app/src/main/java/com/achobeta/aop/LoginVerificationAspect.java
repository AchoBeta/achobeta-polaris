package com.achobeta.aop;

import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 严豪哲
 * @Description: 登录验证拦截器
 * @Date: 2024/11/18 10:27
 * @Version: 1.0
 */

@Slf4j
@Component
@Aspect
public class LoginVerificationAspect {

    private final long EXPIRED = 100*1000;

    private final String ACCESS_TOKEN_NEED_REFRESH = "access_token_need_refresh";

    @Resource
    private ITokenRepository tokenRepository;

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.achobeta.types.constraint.LoginVerification)")
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String token = request.getHeader("access_token");

        if (!checkAT(token)) {
            log.info("accessToken:{}被伪造,是无效token", token);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
        }

        // 获取token的过期时间
        Long accessTokenExpired = tokenRepository.getAccessTokenExpired(token);

        //从redis中获取token信息
        TokenVO tokenVO = tokenRepository.getAccessTokenInfo(token);

        // token校验
        switch(tokenRepository.checkAccessToken(token)){
            case 0:
                log.info("accessToken:{}已过期", token);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
            case -1:
                log.info("accessToken:{}已刷新", token);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_HAVE_REFRESHED.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_HAVE_REFRESHED.getMessage());
            case 1:
                log.info("accessToken:{}校验通过", token);
                break;
            default:
                log.info("accessToken:{}未知错误", token);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getCode()), GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getMessage());
        }

        if(accessTokenExpired <= EXPIRED){
            //如果token是持久化的或者已经超时失效也会进这里
            response.setHeader(ACCESS_TOKEN_NEED_REFRESH, "true");
        }

        //将token信息并将其放在请求头
        request.setAttribute("tokenInfo", tokenVO);

        //执行目标接口
        return joinPoint.proceed();

    }

    private Boolean checkAT(String token) {

        //用tokenUtil检查token是否合法
        if(TokenUtil.checkAccessToken(token)){
            return true;
        }
        else {
            return false;
        }

    }

}
