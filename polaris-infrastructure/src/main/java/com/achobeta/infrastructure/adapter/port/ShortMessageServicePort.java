package com.achobeta.infrastructure.adapter.port;

import com.achobeta.domain.login.adapter.port.IShortMessageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @Author: 严豪哲
 * @Description: 发送短信的的外部接口实现
 * @Date: 2024/11/7 19:35
 * @Version: 1.0
 */

@Component
public class ShortMessageServicePort implements IShortMessageService {

//    @Resource
//    private ShortMessageServiceConfig sms;

    @Override
    public String sms(String phone) {

        // 生成随机六位数字验证码
        Random random = new Random();
        String code = random.nextInt(900000) + 100000 + "";
        // 短信模板参数
        //String[] params={code,sms.getExpireTime()+""};
        // 发送短信
//        try{
//            SmsSingleSender ssender= new SmsSingleSender(sms.getAppId(),sms.getAppKey());
//            SmsSingleSenderResult result=ssender.sendWithParam("86",phone,sms.getTemplateId(),params,sms.getSmsSign(),"","");
//            System.out.println(result);
//        } catch (HTTPException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }

        return code;
    }
}
