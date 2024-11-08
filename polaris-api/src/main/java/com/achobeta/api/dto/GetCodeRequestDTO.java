package com.achobeta.api.dto;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 获取验证码的请求体
 * @Date: 2024/11/5 21:36
 * @Version: 1.0
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCodeRequestDTO {
    //用户的手机号码
    private String phone;
}
