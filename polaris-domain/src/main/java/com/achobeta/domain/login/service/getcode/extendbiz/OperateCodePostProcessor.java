package com.achobeta.domain.login.service.getcode.extendbiz;

import com.achobeta.domain.login.adapter.repository.IOperateCodeRepository;
import com.achobeta.domain.login.model.bo.SendCodeBO;
import com.achobeta.domain.login.model.valobj.CodeVO;
import com.achobeta.domain.login.service.getcode.SendCodePostProcessor;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 验证码的redis操作扩展点
 * @Date: 2024/11/8 20:04
 * @Version: 1.0
 */

@Slf4j
@Component
public class OperateCodePostProcessor implements SendCodePostProcessor {

    private final long EXPIRED = 60*1000;

    @Resource
    private IOperateCodeRepository operateCodeRepository;

    @Override
    public boolean handleBefore(PostContext<SendCodeBO> postContext) {

        CodeVO codeVO = postContext.getBizData().getCodeVO();
        log.info("正在对手机号{}进行频率限制验证",codeVO.getPhone());

        if(operateCodeRepository.checkRateLimit(codeVO.getPhone())){
            log.info("手机号{}的频率限制验证通过,可以发送验证码", codeVO.getPhone());
            return true;
        }
        else{
            log.error("手机号{}的频率限制验证不通过,发送验证码失败", codeVO.getPhone());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_CODE_EXIT.getCode()), GlobalServiceStatusCode.LOGIN_CODE_EXIT.getMessage());
        }
    }

    @Override
    public void handleAfter(PostContext<SendCodeBO> postContext) {
        String code = postContext.getBizData().getCodeVO().getCode();
        String phone = postContext.getBizData().getCodeVO().getPhone();
        log.info("正在存储手机号{}的验证码{}有效时长为{}毫秒",phone,code,EXPIRED);
        operateCodeRepository.setCode(phone,code,EXPIRED);

        log.info("正在对手机号{}进行限流操作",phone);
        operateCodeRepository.setRateLimit(phone);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

}
