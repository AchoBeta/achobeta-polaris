package com.achobeta.domain.login.service.login.extendbiz;

import com.achobeta.domain.login.adapter.repository.IOperateCodeRepository;
import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.domain.login.service.login.LoginPostProcessor;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 验证码校验器
 * @Date: 2024/11/16 17:54
 * @Version: 1.0
 */

@Slf4j
@Component
public class VerifyCodePostProcessor implements LoginPostProcessor {

    @Resource
    private IOperateCodeRepository operateCodeRepository;

    @Override
    public boolean handleBefore(PostContext<LoginBO> postContext) {
        TokenVO tokenVO = postContext.getBizData().getTokenVO();
        String phone = tokenVO.getPhone();
        String code = tokenVO.getCode();
        log.info("正在校验手机号为{}的用户的验证码:{}",phone,code);
        //加锁
        operateCodeRepository.lockCheckCode(phone,code);
        String codeByPhone = operateCodeRepository.getCodeByPhone(phone);
        if(null == codeByPhone){
            log.info("手机号为{}的用户的验证码不存在",phone);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CAPTCHA_CODE_ERROR.getCode()), GlobalServiceStatusCode.USER_CAPTCHA_CODE_ERROR.getMessage());
        }else if(!codeByPhone.equals(code)){
            log.info("手机号为{}的用户的验证码错误",phone);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_CAPTCHA_CODE_ERROR.getCode()), GlobalServiceStatusCode.USER_CAPTCHA_CODE_ERROR.getMessage());
        }else{
            log.info("手机号为{}的用户的验证码正确",phone);
            operateCodeRepository.deleteCode(phone,code);
            //解锁
            operateCodeRepository.unlockCheckCode(phone,code);
            return true;
        }
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
