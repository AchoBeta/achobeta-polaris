package com.achobeta.domain.login.service.getcode.extendbiz;

import com.achobeta.domain.login.adapter.port.IOperateCodePost;
import com.achobeta.domain.login.model.bo.SendCodeBO;
import com.achobeta.domain.login.service.getcode.SendCodePostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 检查验证码是否存在redis中前置扩展点
 * @Date: 2024/11/8 20:04
 * @Version: 1.0
 */

@Slf4j
@Component
public class CheckCodePostProcessor implements SendCodePostProcessor {

    @Resource
    private IOperateCodePost checkCodePost;

    @Override
    public boolean handleBefore(PostContext<SendCodeBO> postContext) {

        SendCodeBO sendCodeBO = postContext.getBizData();
        log.info("正在检查是否存在验证码");
        String code = checkCodePost.getCodeByPhone(sendCodeBO.getPhoneEntity().getPhone());
        if(code == null){
            log.info("验证码不存在");
            return true;
        }
        else{
            log.error("验证码已经存在");
            throw new RuntimeException("访问过于频繁,发送验证码失败");
        }
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE+1;
    }
}
