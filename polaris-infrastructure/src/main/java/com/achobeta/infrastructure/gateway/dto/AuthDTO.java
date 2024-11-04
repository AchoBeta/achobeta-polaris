package com.achobeta.infrastructure.gateway.dto;

import lombok.*;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/4
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDTO {

    private String userId;
    private String userName;
    private String department;
    private Boolean isAuth;
    private Boolean isInWhite;

}