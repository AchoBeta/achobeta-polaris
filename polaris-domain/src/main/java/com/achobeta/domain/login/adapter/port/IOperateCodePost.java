package com.achobeta.domain.login.adapter.port;

/**
 * @Author: 严豪哲
 * @Description: 操作redis中的验证码相关数据外部接口定义
 * @Date: 2024/11/8 20:04
 * @Version: 1.0
 */

public interface IOperateCodePost {

    String getCodeByPhone(String phone);

    void setCode(String phone,String code,long expired);

    boolean checkRateLimit();
}
