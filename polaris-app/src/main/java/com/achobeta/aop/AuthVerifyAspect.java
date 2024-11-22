package com.achobeta.aop;

import com.achobeta.domain.auth.adapter.repository.IAuthRepository;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.types.annotation.AuthVerify;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 鉴权切面
 * @date 2024/11/22
 */
@Aspect
@Component
@Slf4j
public class AuthVerifyAspect {

    @Resource
    private IAuthRepository authRepository;

    @Pointcut("annotation(AuthVerify")
    public void pointCut() {}

    /**
     * 鉴权处理
     * @param point 信息
     * @param authVerify 鉴权注解
     * @return Object
     * @throws Throwable 异常
     */
    @Around("pointCut()")
    public Object verify(ProceedingJoinPoint point, AuthVerify authVerify) throws Throwable {
        Object arg = point.getArgs()[0];
        Method method = arg.getClass().getMethod("getUserId");
        // 获取用户ID，团队ID
        String userId = (String) method.invoke(arg);
        String teamId = (String) method.invoke(arg);
        // 获取需要的角色和权限
        String[] needed = method.getAnnotation(AuthVerify.class).value();
        String[] neededRoles = needed[0].split(":");
        String[] neededPermissions = needed[1].split(":");
        // 查询用户角色和权限
        List<RoleEntity> userRoles = authRepository.queryRoles(userId, teamId);
        List<String> userRoleIds = new ArrayList<>();
        for (RoleEntity userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());
        }
        List<String> userPermissions = authRepository.queryPermissions(userId, userRoleIds);
        if ()

        return point.proceed();
    }

}
