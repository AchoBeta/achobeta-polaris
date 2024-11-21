package com.achobeta.domain.login.service.login.extendbiz;

import com.achobeta.domain.login.adapter.repository.IDeviceRepository;
import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.domain.login.model.entity.DeviceEntity;
import com.achobeta.domain.login.service.login.LoginPostProcessor;
import com.achobeta.types.support.id.SnowflakeIdWorker;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description: 查询或者更新设备扩展接口
 * @Date: 2024/11/13 23:16
 * @Version: 1.0
 */

@Slf4j
@Component
public class OperateDevicePostProcessor implements LoginPostProcessor {

    private final int AUTO_LOGIN = 1;

    private final int NOT_AUTO_LOGIN = 0;

    @Resource
    private IDeviceRepository deviceRepository;

    @Override
    public boolean handleBefore(PostContext<LoginBO> postContext) {

        String ip = postContext.getBizData().getTokenVO().getIp();
        String userId = String.valueOf(postContext.getBizData().getTokenVO().getUserId());
        String deviceName = postContext.getBizData().getDeviceName();
        Boolean autoLogin = postContext.getBizData().getTokenVO().getIsAutoLogin();

        log.info("正在查询用户的设备信息,userId:{}", userId);
        DeviceEntity deviceEntity = deviceRepository.getDeviceByIp(userId, ip);

        if (null == deviceEntity) {
            log.info("设备{}不存在，正在创建,userId:{}", ip, userId);
            deviceEntity = DeviceEntity.builder()
                    .deviceId(SnowflakeIdWorker.nextIdStr())
                    .deviceName(deviceName)
                    .userId(String.valueOf(postContext.getBizData().getTokenVO().getUserId()))
                    .IP(ip)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .isCancel(autoLogin? 1 : 0)
                    .build();
            deviceRepository.insertDevice(deviceEntity);
            log.info("设备{}创建成功,userId:{}", ip, userId);
        }
        else{
            log.info("用户的设备{}已存在,userId:{}", ip, userId);
            if(autoLogin) {
                log.info("检测到用户开启了自动登录,正在更新设备{},userId:{}", ip, userId);
                deviceRepository.updateDevice(deviceEntity.getDeviceId(), LocalDateTime.now(), AUTO_LOGIN);
                log.info("设备{}更新成功,userId:{}", ip, userId);
            }
            else{
                log.info("用户没有开启自动登录,正在更新设备{}最近登录时间,userId:{}", ip, userId);
                deviceRepository.updateDevice(deviceEntity.getDeviceId(), LocalDateTime.now(), NOT_AUTO_LOGIN);
                log.info("设备{}更新成功,userId:{}", ip, userId);
            }
        }

        postContext.getBizData().getTokenVO().setDeviceId(deviceEntity.getDeviceId());
        return true;
    }


    @Override
    public int getPriority() {
        return Integer.MIN_VALUE + 2;
    }
}
