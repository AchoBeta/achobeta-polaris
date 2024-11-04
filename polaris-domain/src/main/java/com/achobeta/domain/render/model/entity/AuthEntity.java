package com.achobeta.domain.render.model.entity;

import lombok.*;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthEntity {

    /** 用户实体 */
    private UserEntity userEntity;
    /** 用户是否具有权限 */
    private Boolean isAuth;
    /** 用户是否在白名单 */
    private Boolean isInWhite;

}
