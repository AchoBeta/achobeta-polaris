package com.achobeta.api.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author: 严豪哲
 * @Description: 登录请求DTO
 * @Date: 2024/11/9 10:07
 * @Version: 1.0
 */

//phone,code,ip,is_auto_login
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    /*
     * 登录用户的手机号
     */
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "请检查手机号是否正确")
    private String phone;

    /*
     * 登录用户的验证码
     */
    @Pattern(regexp = "^\\d{6}$", message = "验证码错误,请重试")
    private String code;

    /*
     * 登录用户的ip
     */
    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", message = "ip格式错误")
    private String ip;

    /*
     * 登录用户的是否自动登录
     */
    @Pattern(regexp = "^(true|false)$", message = "是否自动登录只能是true或者false")
    private String isAutoLogin;

    /*
     * 登录用户的是否自动登录
     */
    private boolean autoLogin;

    /*
     * 登录用户的设备指纹
     */
    @Size(max = 46, message = "设备指纹长度不能超过46")
    private String fingerPrinting;

}
