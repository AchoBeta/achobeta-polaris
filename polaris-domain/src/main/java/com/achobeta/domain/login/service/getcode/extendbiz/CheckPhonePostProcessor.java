package com.achobeta.domain.login.service.getcode.extendbiz;

import com.achobeta.domain.login.model.bo.SendCodeBO;
import com.achobeta.domain.login.service.getcode.SendCodePostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import com.achobeta.types.support.util.CheckPhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Author: 严豪哲
 * @Description: 检查手机号是否合法扩展点实现
 * @Date: 2024/11/7 15:53
 * @Version: 1.0
 */

@Slf4j
@Component
public class CheckPhonePostProcessor implements SendCodePostProcessor {

    @Override
    public boolean handleBefore(PostContext<SendCodeBO> postContext) {
        SendCodeBO sendCodeBO = postContext.getBizData();
        String phone = sendCodeBO.getPhoneEntity().getPhone();
        log.info("正在检查手机号{}是否合法",phone);
        if(!CheckPhoneUtil.checkPhone(phone)){
            log.error("手机号{}不合法",phone);
            throw new RuntimeException("手机号不合法");
        }
        else{
            log.info("手机号{}合法",phone);
            return true;
        }
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
