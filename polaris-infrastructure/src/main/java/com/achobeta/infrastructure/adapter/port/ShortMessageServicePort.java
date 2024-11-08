package com.achobeta.infrastructure.adapter.port;

import com.achobeta.domain.login.adapter.port.IShortMessageService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: 严豪哲
 * @Description: 发送短信的的外部接口实现
 * @Date: 2024/11/7 19:35
 * @Version: 1.0
 */

@Component
public class ShortMessageServicePort implements IShortMessageService {
    @Override
    public String sms(String phone) {
        // 短信应用SDK AppID 短信应用的唯一标识，登录短信控制台后，在应用管理中查看
        int appId=0;
        // 短信应用SDK AppKey 密码
        String appKey ="";
        // 短信模板ID
        int templateId=0;
        // 签名
        String smsSign="A";
        // 生成随机六位数字验证码
        Random random = new Random();
        String code = random.nextInt(900000) + 100000 + "";
        //设置验证码的过期时间为5分钟
        long expireTime = System.currentTimeMillis() + 5 * 60 * 1000;
        // 短信模板参数
        String[] params={code,expireTime+""};
        // 发送短信
//        try{
//            SmsSingleSender ssender= new SmsSingleSender(appId,appKey);
//            SmsSingleSenderResult result=ssender.sendWithParam("86",phone,templateId,params,smsSign,"","");
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
