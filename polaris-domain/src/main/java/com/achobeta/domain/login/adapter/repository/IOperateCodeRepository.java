package com.achobeta.domain.login.adapter.repository;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码相关数据外部接口定义
 * @Date: 2024/11/8 20:04
 * @Version: 1.0
 */

public interface IOperateCodeRepository {

    /*
     * 从redis中获取验证码
     * @param phone 手机号
     * @return 验证码
     */
    String getCodeByPhone(String phone);

    /*
     * 将验证码存入redis中
     * @param phone 手机号
     * @param code 验证码
     * @param expired 过期时间
     */
    void setCode(String phone,String code,long expired);

}
