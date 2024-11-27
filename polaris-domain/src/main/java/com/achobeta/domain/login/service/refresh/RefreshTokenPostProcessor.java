package com.achobeta.domain.login.service.refresh;

import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.types.support.postprocessor.BasePostProcessor;

/**
 * @Author: 严豪哲
 * @Description: 刷新token的扩展接口
 * @Date: 2024/11/17 15:56
 * @Version: 1.0
 */
public interface RefreshTokenPostProcessor extends BasePostProcessor<LoginBO> {

}
