package com.achobeta.domain.login.model.bo;

import com.achobeta.domain.login.model.entity.PhoneEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 严豪哲
 * @Description: 发送验证码的BO
 * @Date: 2024/11/6 21:18
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendCodeBO {
    private PhoneEntity phoneEntity;
    private String code;
}
