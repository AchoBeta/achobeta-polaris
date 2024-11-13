package com.achobeta.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author chensongmin
 * @description 全局服务响应状态码枚举
 * @date 2024/11/11
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum GlobalServiceStatusCode {
    /* 成功, 默认200 */
    SYSTEM_SUCCESS(200, "操作成功"),

    /* 系统错误 负数 */
    SYSTEM_SERVICE_FAIL(-4396, "操作失败"),
    SYSTEM_SERVICE_ERROR(-500, "系统异常"),
    SYSTEM_TIME_OUT(-1, "请求超时"),

    /* 参数错误：1001～2000 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    PARAM_FAILED_VALIDATE(1005, "参数未通过验证"),

    REQUEST_NOT_VALID(1101, "请求无效"),

    /* 用户错误 2001-3000 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_REGISTER_ERROR(2010, "账号注册错误"),

    USER_TYPE_EXCEPTION(2101, "用户类别异常"),

    USER_NO_PERMISSION(2403, "用户无权限"),
    USER_CAPTCHA_CODE_ERROR(2500, "验证码错误"),
    ;

    private Integer code;
    private String message;

}
