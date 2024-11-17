package com.achobeta.domain.login.adapter.repository;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码相关数据外部接口定义
 * @Date: 2024/11/8 20:04
 * @Version: 1.0
 */

public interface IOperateCodeRepository {

    /**
     * 从redis中获取验证码
     * @param phone 手机号
     * @return 验证码
     */
    String getCodeByPhone(String phone);

    /**
     * 将验证码存入redis中
     * @param phone 手机号
     * @param code 验证码
     * @param expired 过期时间
     */
    void setCode(String phone,String code,long expired);

    /**
     * 设置手机号的发送验证码频率限制
     * @param phone
     */
    void setRateLimit(String phone);

    /**
     * 检查手机号是否达到发送验证码的频率限制
     * @param phone 手机号
     * @return 是否达到频率限制
     */
    Boolean checkRateLimit(String phone);

    /**
     * 从redis中删除验证码
     * @param phone 手机号
     * @param code 验证码
     */
    void deleteCode(String phone, String code);

    /**
     * 在redis中校验验证码是否正确时的加锁操作
     * @param phone 手机号
     * @param code 验证码
     */
    void lockCheckCode(String phone, String code);

    /**
     * 在redis中校验验证码是否正确时的解锁操作
     * @param phone 手机号
     * @param code 验证码
     */
    void unlockCheckCode(String phone, String code);
}
