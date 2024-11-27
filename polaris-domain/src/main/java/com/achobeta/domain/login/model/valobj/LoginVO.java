package com.achobeta.domain.login.model.valobj;

import com.achobeta.domain.team.model.entity.PositionEntity;
import lombok.*;

import java.util.List;

/**
 * @Author: 严豪哲
 * @Description: 登录接口返回值对象
 * @Date: 2024/11/9 21:15
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginVO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户的团队信息
     */
    private List<PositionEntity> positionList;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 设备id
     */
    private String deviceId;
}
