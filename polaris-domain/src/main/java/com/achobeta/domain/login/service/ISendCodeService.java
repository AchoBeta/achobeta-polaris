package com.achobeta.domain.login.service;


import com.achobeta.domain.login.model.valobj.CodeVO;

/**
 * @Author: 严豪哲
 * @Description: 发送验证码服务接口定义
 * @Date: 2024/11/6 21:18
 * @Version: 1.0
 */

public interface ISendCodeService {
    /*
     * 发送验证码
     * @param phone 手机号
     */

    void sendCode(String phone);
}
