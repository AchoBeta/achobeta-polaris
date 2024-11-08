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
 * @Description: 针对该接口访问限流扩展点实现
 * @Date: 2024/11/8 21:38
 * @Version: 1.0
 */
@Slf4j
@Component
public class CheckRateLimitPostProcessor implements SendCodePostProcessor {

    @Resource
    private IOperateCodePost operateCodePost;

    @Override
    public boolean handleBefore(PostContext<SendCodeBO> postContext) {
        log.info("正在检查访问频率");
        boolean check = operateCodePost.checkRateLimit();
        if(!check){
            log.error("系统访问过于频繁");
            throw new RuntimeException("系统访问过于频繁");
        }else{
            log.info("目前系统访问频率正常");
            return true;
        }
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE+2;
    }
}
