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
 * @Description: 存储验证码后置扩展点实现
 * @Date: 2024/11/8 20:24
 * @Version: 1.0
 */
@Slf4j
@Component
public class StoreCodePostProcessor implements SendCodePostProcessor {

    @Resource
    private IOperateCodePost checkCodePost;

    @Override
    public void handleAfter(PostContext<SendCodeBO> postContext) {
        String code = postContext.getBizData().getCode();
        String phone = postContext.getBizData().getPhoneEntity().getPhone();
        //long expired = 50*1000;
        long expired = 10000;
        log.info("正在存储手机号{}的验证码{}有效时长为{}毫秒",phone,code,expired);
        checkCodePost.setCode(phone,code,expired);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }
}
