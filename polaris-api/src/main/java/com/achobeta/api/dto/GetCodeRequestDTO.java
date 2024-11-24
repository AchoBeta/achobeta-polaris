package com.achobeta.api.dto;

import lombok.*;
import javax.validation.constraints.Pattern;

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
    /*
     * 用户的手机号码
     */
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "请检查手机号码是否正确")
    private String phone;
}
