package com.achobeta.infrastructure.adapter.port;

import com.achobeta.domain.render.adapter.port.IAuthPort;
import com.achobeta.domain.render.model.entity.AuthEntity;
import com.achobeta.domain.render.model.entity.UserEntity;
import com.achobeta.infrastructure.gateway.AuthRPC;
import com.achobeta.infrastructure.gateway.dto.AuthDTO;
import org.springframework.stereotype.Component;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Component
public class AuthPort implements IAuthPort {

    private final AuthRPC authRPC;

    public AuthPort(AuthRPC authRPC) {
        this.authRPC = authRPC;
    }

    @Override
    public AuthEntity queryUserAuth(String userId) {
        AuthDTO authDTO = authRPC.queryUserAuth(userId);
        return AuthEntity.builder()
                .isAuth(authDTO.getIsAuth())
                .isInWhite(authDTO.getIsInWhite())
                .userEntity(UserEntity.builder()
                        .userId(authDTO.getUserId())
                        .userName(authDTO.getUserName())
                        .department(authDTO.getDepartment())
                        .build())
                .build();
    }

}
