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

    private String accessToken;
    private String refreshToken;
    private String userId;
    private List<PositionEntity> positionList;
    private String phone;
}
