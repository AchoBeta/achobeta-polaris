package com.achobeta.domain.login.model.bo;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 登出业务BO
 * @Date: 2024/11/19 21:00
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogoutBO {

    /**
     * 刷新令牌
     */
    private String accessToken;

    /**
     * 设备ID
     */
    private String deviceId;

}
