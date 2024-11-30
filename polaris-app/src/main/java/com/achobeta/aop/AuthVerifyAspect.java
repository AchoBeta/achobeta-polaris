package com.achobeta.aop;

import cn.hutool.core.util.ArrayUtil;
import com.achobeta.domain.auth.adapter.repository.IAuthRepository;
import com.achobeta.domain.auth.model.entity.RoleEntity;
import com.achobeta.types.Response;
import com.achobeta.types.annotation.AuthVerify;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.achobeta.types.enums.GlobalServiceStatusCode.TEAM_INVALID_OPERATION;
import static com.achobeta.types.enums.GlobalServiceStatusCode.USER_NO_PERMISSION;

/**
 * @author yangzhiyao
 * @description 鉴权切面
 * @date 2024/11/22
 */
@Aspect
@Component
@Slf4j
@Order(Integer.MIN_VALUE+2)
public class AuthVerifyAspect {

    @Resource
    private IAuthRepository authRepository;

    /**
     * 鉴权处理
     * @param point 信息
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(com.achobeta.types.annotation.AuthVerify)")
    public Object verify(ProceedingJoinPoint point) throws Throwable {
        Object arg = point.getArgs()[0];
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 获取用户ID，团队ID
        String userId = (String) arg.getClass().getMethod("getUserId").invoke(arg);
        String teamId = (String) arg.getClass().getMethod("getTeamId").invoke(arg);
        // 获取需要权限
        String[] needed = method.getAnnotation(AuthVerify.class).value().split(":");
        // 查询用户角色和权限
        List<RoleEntity> userRoles = authRepository.queryRoles(userId, teamId);
        List<String> userRoleIds = new ArrayList<>();
        for (RoleEntity userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());
        }
        List<String> userPermissions = authRepository.queryPermissions(userId, userRoleIds, teamId);
        // 对未选择团队相关进行特判
        if ("0000".equals(teamId)) {
            if (checkNoTeam(signature.getName())) {
                return Response.CUSTOMIZE_ERROR(TEAM_INVALID_OPERATION);
            } else if (!userPermissions.contains("SUPER")) {
                return Response.CUSTOMIZE_ERROR(USER_NO_PERMISSION);
            }
        }
        // 判断用户是否有权限
        if (ArrayUtil.isEmpty(needed) || userPermissions.contains("SUPER")) {
            return point.proceed();
        }
        for (String neededPermission : needed) {
            if (userPermissions.contains(neededPermission)) {
                return point.proceed();
            }
        }
        log.error("用户 {} 在团队 {} 无权限访问方法 {} ", userId, teamId, point.getSignature().getName());
        return Response.CUSTOMIZE_ERROR(USER_NO_PERMISSION);
    }

    private static Boolean checkNoTeam(String methodName) {
        return !("queryMemberList".equals(methodName)
                || "queryRoles".equals(methodName)
                || "queryMemberInfo".equals(methodName)
                || "queryStructure".equals(methodName));
    }
}
