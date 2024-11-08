package com.achobeta.domain.login.service;


import com.achobeta.domain.login.model.valobj.SendCodeVO;

/**
 * @Author: 严豪哲
 * @Description: 发送验证码服务接口
 * @Date: 2024/11/6 21:18
 * @Version: 1.0
 */

public interface ISendCodeService {
    SendCodeVO sendCode(String phone);
}
