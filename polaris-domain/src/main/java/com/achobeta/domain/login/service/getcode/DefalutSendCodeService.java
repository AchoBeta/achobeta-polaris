package com.achobeta.domain.login.service.getcode;

import com.achobeta.domain.login.adapter.port.IShortMessageService;
import com.achobeta.domain.login.model.bo.SendCodeBO;
import com.achobeta.domain.login.model.entity.PhoneEntity;
import com.achobeta.domain.login.model.valobj.SendCodeVO;
import com.achobeta.domain.login.service.ISendCodeService;
import com.achobeta.types.common.Constants;
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
    public SendCodeVO sendCode(String phone) {
        PostContext<SendCodeBO> postContext = PostContext.<SendCodeBO>builder()
                .bizName(Constants.BizModule.LOGIN.getName())
                .bizData(SendCodeBO.builder()
                        .phoneEntity(PhoneEntity.builder().phone(phone).build())
                        .build())
                .build();
        postContext = super.doPostProcessor(postContext, SendCodePostProcessor.class);
        return SendCodeVO.builder()
                .result(postContext.getBizData().getPhoneEntity().getPhone())
                .build();
    }

    @Override
    public PostContext<SendCodeBO> doMainProcessor(PostContext<SendCodeBO> postContext) {
        String phone = postContext.getBizData().getPhoneEntity().getPhone();
        log.info("正在为手机号{}发送验证码", phone);
        String code = shortMessageService.sms(phone);
        postContext.getBizData().setCode(code);
        log.info("手机号{}的验证码{}发送成功", phone, code);
        return postContext;
    }

    @Override
    public PostContext<SendCodeBO> doInterruptMainProcessor(PostContext<SendCodeBO> postContext) {
        log.info("手机号{}的验证码发送失败", postContext.getBizData().getPhoneEntity().getPhone());
        postContext.getBizData().getPhoneEntity().setPhone(null);
        throw new RuntimeException("验证码发送失败");
    }
}
