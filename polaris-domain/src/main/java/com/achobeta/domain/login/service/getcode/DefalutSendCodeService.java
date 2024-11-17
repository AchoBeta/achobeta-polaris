package com.achobeta.domain.login.service.getcode;

import com.achobeta.domain.login.adapter.port.IShortMessageService;
import com.achobeta.domain.login.model.bo.SendCodeBO;
import com.achobeta.domain.login.model.valobj.CodeVO;
import com.achobeta.domain.login.service.ISendCodeService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 严豪哲
 * @Description: 默认发送验证码服务实现类
 * @Date: 2024/11/6 12:05
 * @Version: 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DefalutSendCodeService extends AbstractPostProcessor<SendCodeBO> implements ISendCodeService {

    @Resource
    private IShortMessageService shortMessageService;

    @Override
    public void sendCode(String phone) {
        PostContext<SendCodeBO> postContext = buildPostContext(phone);
        postContext = super.doPostProcessor(postContext, SendCodePostProcessor.class);
    }

    @Override
    public PostContext<SendCodeBO> doMainProcessor(PostContext<SendCodeBO> postContext) {
        String phone = postContext.getBizData().getCodeVO().getPhone();
        log.info("正在为手机号{}发送验证码", phone);
        String code = shortMessageService.sms(phone);
        postContext.getBizData().getCodeVO().setCode(code);
        log.info("手机号{}的验证码{}发送成功", phone, code);
        return postContext;
    }

    @Override
    public PostContext<SendCodeBO> doInterruptMainProcessor(PostContext<SendCodeBO> postContext) {
        log.info("手机号{}的验证码发送失败", postContext.getBizData().getCodeVO().getPhone());
        throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_SMS_SEND_FAIL), GlobalServiceStatusCode.LOGIN_SMS_SEND_FAIL.getMessage());
    }

    private static PostContext<SendCodeBO> buildPostContext(String phone) {
        return PostContext.<SendCodeBO>builder()
                .bizName(BizModule.LOGIN.getCode())
                .bizData(SendCodeBO.builder()
                        .codeVO(CodeVO.builder()
                                .phone(phone)
                                .build()
                        )
                        .build())
                .build();
    }
}
