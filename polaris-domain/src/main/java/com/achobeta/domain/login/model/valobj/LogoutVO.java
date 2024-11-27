package com.achobeta.domain.login.model.valobj;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 登出值对象
 * @Date: 2024/11/19 21:05
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogoutVO {

    /**
     * 设备id
     */
    private String deviceId;
}
