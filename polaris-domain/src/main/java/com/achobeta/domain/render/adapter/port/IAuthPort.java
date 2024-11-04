package com.achobeta.domain.render.adapter.port;

import com.achobeta.domain.render.model.entity.AuthEntity;

/**
 * @author chensongmin
 * @description 鉴权外部接口
 * !!注意这里只是举个例子，我们项目可以不用这种这种方式，我打算考虑使用注解 + AOP 做全局鉴权!!
 * @date 2024/11/4
 */
public interface IAuthPort {

    /**
     * 查询用户权限信息
     *
     * @return
     */
    AuthEntity queryUserAuth(String userId);

}
