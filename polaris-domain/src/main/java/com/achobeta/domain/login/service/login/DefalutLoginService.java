package com.achobeta.domain.login.service.login;

import com.achobeta.domain.login.adapter.port.ITeamInfoPort;
import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.domain.login.model.valobj.LoginVO;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.domain.login.service.IAuthorizationService;
import com.achobeta.domain.team.model.entity.PositionEntity;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import com.achobeta.types.support.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 严豪哲
 * @Description: 默认登录服务实现类
 * @Date: 2024/11/9 21:04
 * @Version: 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DefalutLoginService extends AbstractPostProcessor<LoginBO> implements IAuthorizationService {

    @Resource
    private ITeamInfoPort teamInfoPort;

    @Resource
    private ITokenRepository tokenRepository;

    @Override
    public LoginVO login(String phone, String code, String ip, Boolean isAutoLogin, String deviceName, String mac) {
        PostContext<LoginBO> postContext = buildPostContext(phone, code, ip, isAutoLogin, deviceName, mac);
        postContext = super.doPostProcessor(postContext, LoginPostProcessor.class);
        return LoginVO.builder()
                .userId(String.valueOf(postContext.getBizData().getTokenVO().getUserId()))
                .positionList(postContext.getBizData().getPositionList())
                .accessToken(postContext.getBizData().getTokenVO().getAccessToken())
                .refreshToken(postContext.getBizData().getTokenVO().getRefreshToken())
                .build();
    }

    @Override
    public PostContext<LoginBO> doMainProcessor(PostContext<LoginBO> postContext) {
        TokenVO tokenVO = postContext.getBizData().getTokenVO();
        log.info("正在生成新的AT和RT,userId:{}", tokenVO.getUserId());
        String accessToken = TokenUtil.getAccessToken(tokenVO.getUserId(), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getMac());
        String refreshToken = TokenUtil.getRefreshToken(tokenVO.getUserId(), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getIsAutoLogin(), tokenVO.getMac());
        tokenVO.setAccessToken(accessToken);
        tokenVO.setRefreshToken(refreshToken);
        log.info("AT和RT生成成功,userId:{}", tokenVO.getUserId());

        log.info("正在将AT和RT存入redis,userId:{}", tokenVO.getUserId());

        // 调用RedisService的storeAccessToken和storeReflashToken方法将accessToken和refreshToken存入Redis
        tokenRepository.storeAccessToken(accessToken, String.valueOf(tokenVO.getUserId()), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getMac());
        tokenRepository.storeReflashToken(refreshToken, String.valueOf(tokenVO.getUserId()), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getIsAutoLogin(), tokenVO.getMac());

        log.info("AT和RT存入redis成功,userId:{}", tokenVO.getUserId());

        log.info("正在查询用户团队信息,userId:{}", tokenVO.getUserId());
        List<PositionEntity> positionEntities = teamInfoPort.queryTeamByUserId(String.valueOf(tokenVO.getUserId()));
        postContext.getBizData().setPositionList(positionEntities);
        log.info("用户团队信息查询成功,userId:{}", tokenVO.getUserId());

        postContext.setBizData(LoginBO.builder()
                .tokenVO(tokenVO)
                .build());

        return postContext;
    }

    public static PostContext<LoginBO> buildPostContext(String phone, String code, String ip, Boolean isAutoLogin, String deviceName, String mac) {
        return PostContext.<LoginBO>builder()
                .bizName(BizModule.LOGIN.getName())
                .bizData(LoginBO.builder()
                        .tokenVO(
                                TokenVO.builder()
                                        .phone(phone)
                                        .code(code)
                                        .ip(ip)
                                        .isAutoLogin(isAutoLogin)
                                        .mac(mac)
                                        .build())
                        .deviceName(deviceName)
                        .build())
                .build();
    }

}
