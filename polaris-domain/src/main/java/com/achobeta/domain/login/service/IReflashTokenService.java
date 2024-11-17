package com.achobeta.domain.login.service;

import com.achobeta.domain.login.model.valobj.LoginVO;

/**
 * @Author: 严豪哲
 * @Description: 刷新token服务接口
 * @Date: 2024/11/17 15:56
 * @Version: 1.0
 */
public interface IReflashTokenService {

    /**
     * 刷新token
     * @param
     * @return
     */
    LoginVO reflash(String reflashToken);
}
