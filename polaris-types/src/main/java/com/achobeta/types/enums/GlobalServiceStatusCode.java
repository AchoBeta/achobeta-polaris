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
    USER_ACCOUNT_NOT_EXIST(2007, "暂无此账号，请联系管理员"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_REGISTER_ERROR(2010, "账号注册错误"),

    USER_TYPE_EXCEPTION(2101, "用户类别异常"),

    USER_NO_PERMISSION(2403, "用户无权限"),
    USER_CAPTCHA_CODE_ERROR(2500, "验证码错误,请重试"),

    /* 团队异常 3001-4000 */
    TEAM_NOT_EXIST(3001, "团队不存在"),
    TEAM_NOT_JOIN(3002, "团队未加入"),
    TEAM_NOT_NUMBER(3003, "团队成员不存在"),

    TEAM_STRUCTURE_ADD_INVALID(3101, "无效的职位/分组添加"),
    TEAM_STRUCTURE_DELETE_INVALID(3102, "无效的职位/分组删除"),

    /* 登录模块错误 6001-7000*/
    LOGIN_CODE_EXIT(6001,"需要等待一段时间后才能再次尝试"),
    LOGIN_SMS_SEND_FAIL(6002, "验证码发送失败"),
    LOGIN_REFLASH_TOKEN_MISSING(6003, "refreshToken缺失"),
    LOGIN_REFRESH_TOKEN_EXPIRED(6004, "refreshToken已过期"),
    LOGIN_REFRESH_TOKEN_INVALID(6005, "refreshToken无效"),
    LOGIN_ACCESS_TOKEN_MISSING(6006, "accessToken缺失"),
    LOGIN_ACCESS_TOKEN_EXPIRED(6007, "accessToken已过期"),
    LOGIN_ACCESS_TOKEN_INVALID(6008, "accessToken无效"),
    LOGIN_ACCESS_TOKEN_NEED_REFLAH(6009, "accessToken即将过期，需要刷新"),
    LOGIN_FAILED_TO_FORCE_LOGOUT(6010, "强制下线失败"),
    LOGIN_FAILED_TO_LOGOUT(6011, "登出失败"),
    LOGIN_UNKNOWN_ERROR(6012, "登陆模块未知错误,请联系管理员"),
    LOGIN_ACCESS_TOKEN_HAVE_REFRESHED(6013, "accessToken已被刷新,请重新发送请求"),
    ;

    private Integer code;
    private String message;

}
