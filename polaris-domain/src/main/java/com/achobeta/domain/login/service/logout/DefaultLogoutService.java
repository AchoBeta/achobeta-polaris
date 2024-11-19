package com.achobeta.domain.login.service.logout;

import com.achobeta.domain.login.adapter.repository.IDeviceRepository;
import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.bo.LogoutBO;
import com.achobeta.domain.login.model.valobj.LogoutVO;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.domain.login.service.IUserLogoutService;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/19 20:59
 * @Version: 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultLogoutService extends AbstractPostProcessor<LogoutBO> implements IUserLogoutService{

    @Resource
    private ITokenRepository tokenRepository;

    @Resource
    private IDeviceRepository deviceRepository;

    @Override
    public LogoutVO logout(String accessToken, String deviceId) {
        PostContext<LogoutBO> postContext = buildPostContext(accessToken, deviceId);
        super.doPostProcessor(postContext, LogoutPostProcessor.class);
        return LogoutVO.builder()
              .build();
    }

    @Override
    public PostContext<LogoutBO> doMainProcessor(PostContext<LogoutBO> postContext) {

        String accessToken = postContext.getBizData().getAccessToken();
        String deviceId = postContext.getBizData().getDeviceId();

        TokenVO accessTokenInfo = tokenRepository.getAccessTokenInfo(accessToken);
        if (accessTokenInfo == null){
            log.info("accessToken已过期,请刷新", accessToken);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_EXPIRED.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_EXPIRED.getMessage());
        }

        if(null == deviceId || deviceId.equals(accessTokenInfo.getDeviceId())){
            deviceId = accessTokenInfo.getDeviceId();
        }
        else if(accessTokenInfo.getUserId().toString().equals(deviceRepository.getUserIdByDeviceId(deviceId))){}
        else {
            log.info("用户{}要下线的设备{}不属于自己,下线失败", accessTokenInfo.getUserId().toString(), deviceId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_FAILED_TO_FORCE_LOGOUT.getCode()), GlobalServiceStatusCode.LOGIN_FAILED_TO_FORCE_LOGOUT.getMessage());
        }

        String userId = accessTokenInfo.getUserId().toString();

        log.info("正在进行登出,userId:{},deviceId:{}", userId, deviceId);
        log.info("正在删除该设备的所有token,userId:{},deviceId:{}", userId, deviceId);
        tokenRepository.deleteTokenByDeviceId(deviceId);
        log.info("正在更新设备表,userId:{},deviceId:{}", userId, deviceId);
        deviceRepository.updateDevice(deviceId, LocalDateTime.now(), 0);
        log.info("用户{}的设备{}已下线", accessTokenInfo.getUserId(), deviceId);

        return postContext;
    }

    @Override
    public PostContext<LogoutBO> doInterruptMainProcessor(PostContext<LogoutBO> postContext) {
        return super.doInterruptMainProcessor(postContext);
    }

    public static PostContext<LogoutBO> buildPostContext(String accessToken, String deviceId){
        return PostContext.<LogoutBO>builder()
                .bizName(BizModule.LOGIN.getName())
                .bizData(LogoutBO.builder()
                        .accessToken(accessToken)
                        .deviceId(deviceId)
                        .build())
                .build();
    }

}
