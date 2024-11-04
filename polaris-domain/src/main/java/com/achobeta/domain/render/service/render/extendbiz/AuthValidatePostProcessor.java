package com.achobeta.domain.render.service.render.extendbiz;

import com.achobeta.domain.render.adapter.port.IAuthPort;
import com.achobeta.domain.render.model.bo.RenderBO;
import com.achobeta.domain.render.model.entity.AuthEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import com.achobeta.domain.render.service.render.RenderBookPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chensongmin
 * @description 权限校验扩展点实现
 * 无论什么业务场景，权限校验在主流程前，优先级直接定义成最小
 * !!注意这里只是举个例子，我们项目可以不用这种这种方式，我打算考虑使用注解 + AOP 做全局鉴权!!
 * @create 2024/11/3
 */
@Slf4j
@Component
public class AuthValidatePostProcessor implements RenderBookPostProcessor {

    @Resource
    private IAuthPort authPort;

    @Override
    public boolean handleBefore(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserEntity userEntity = renderBO.getUserEntity();
        AuthEntity authEntity = authPort.queryUserAuth(userEntity.getUserId());
        if (!authEntity.getIsAuth() && !authEntity.getIsInWhite()) {
            log.error("鉴权失败! 用户:{} 部门:{} 暂无访问 renderText 方法权限!",
                    authEntity.getUserEntity().getUserName(),
                    authEntity.getUserEntity().getDepartment());
            throw new RuntimeException("鉴权失败! 暂无访问 renderText 方法权限!");
        }
        renderBO.setUserEntity(authEntity.getUserEntity());
        postContext.setBizData(renderBO);
        // 不干预主流程继续执行
        return true;
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
