package com.achobeta.domain.login.service.refresh;

import com.achobeta.domain.login.adapter.port.ITeamInfoPort;
import com.achobeta.domain.login.adapter.repository.ITokenRepository;
import com.achobeta.domain.login.model.bo.LoginBO;
import com.achobeta.domain.login.model.valobj.LoginVO;
import com.achobeta.domain.login.model.valobj.TokenVO;
import com.achobeta.domain.login.service.IRefreshTokenService;
import com.achobeta.domain.team.model.entity.TeamEntity;
import com.achobeta.types.constraint.CheckRT;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
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
 * @Description: 默认刷新token服务实现类
 * @Date: 2024/11/17 15:56
 * @Version: 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRefreshTokenService extends AbstractPostProcessor<LoginBO> implements IRefreshTokenService {

    @Resource
    private ITokenRepository tokenRepository;

    @Resource
    private ITeamInfoPort teamInfoPort;

    @CheckRT
    @Override
    public LoginVO reflash(String refreshToken) {
        log.info("正在从redis中获取token:{}携带的信息", refreshToken);
        TokenVO tokenInfo = tokenRepository.getReflashTokenInfo(refreshToken);
        if (null == tokenInfo) {
            log.info("refreshToken:{}已经失效,请重新登录", refreshToken);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_EXPIRED.getMessage());
        }
        log.info("从redis中获取token携带的信息成功,", refreshToken);
        PostContext<LoginBO> postContext = buildPostContext(tokenInfo, refreshToken);
        postContext = super.doPostProcessor(postContext, RefreshTokenPostProcessor.class);
        return LoginVO.builder()
                .userId(String.valueOf(postContext.getBizData().getTokenVO().getUserId()))
                .teams(postContext.getBizData().getTeams())
                .accessToken(postContext.getBizData().getTokenVO().getAccessToken())
                .refreshToken(postContext.getBizData().getTokenVO().getRefreshToken())
                .phone(postContext.getBizData().getTokenVO().getPhone())
                .deviceId(postContext.getBizData().getTokenVO().getDeviceId())
                .build();
    }

    @Override
    public PostContext<LoginBO> doMainProcessor(PostContext<LoginBO> postContext) {

        TokenVO tokenVO = postContext.getBizData().getTokenVO();
        log.info("正在生成的AT,userId:{}", tokenVO.getUserId());
        String accessToken = TokenUtil.getAccessToken(tokenVO.getUserId(), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getFingerPrinting());
        tokenVO.setAccessToken(accessToken);
        log.info("AT生成成功,userId:{}", tokenVO.getUserId());

        log.info("正在将AT存入redis,userId:{}", tokenVO.getUserId());

        // 调用RedisService的storeAccessToken将accessToken存入Redis
        // 同时也会将前一个AT删除
        tokenRepository.storeAccessToken(accessToken, String.valueOf(tokenVO.getUserId()), tokenVO.getPhone(), tokenVO.getDeviceId(), tokenVO.getIp(), tokenVO.getFingerPrinting());

        log.info("AT存入redis成功,userId:{}", tokenVO.getUserId());

        if (!tokenVO.getIsAutoLogin()) {
            log.info("检测到用户未开启自动登录,正在重置reflashToken的有效时间,userId:{}", tokenVO.getUserId());
            // 调用resetRefreshTokenExpiration将refreshToken的有效时间重置为12小时
            tokenRepository.resetReflashTokenExpired(tokenVO.getRefreshToken());
        } else {
            log.info("检测到用户开启自动登录,无需重置reflashToken的有效时间,userId:{}", tokenVO.getUserId());
        }

        log.info("正在查询用户团队信息,userId:{}", tokenVO.getUserId());
        List<TeamEntity> teams = teamInfoPort.queryTeamByUserId(String.valueOf(tokenVO.getUserId()));
        log.info("用户团队信息查询成功,userId:{}", tokenVO.getUserId());

        postContext.setBizData(LoginBO.builder()
                .tokenVO(tokenVO)
                .teams(teams)
                .build());
        return postContext;
    }

    @Override
    public PostContext doInterruptMainProcessor(PostContext<LoginBO> postContext) {
        log.info("刷新token失败,请重新登录,userId:{}", postContext.getBizData().getTokenVO().getUserId());
        throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
    }

    private static PostContext<LoginBO> buildPostContext(TokenVO tokenInfo, String refreshToken) {

        PostContext<LoginBO> build = PostContext.<LoginBO>builder()
                .bizName(BizModule.LOGIN.getCode())
                .bizData(LoginBO.builder()
                        .tokenVO(tokenInfo)
                        .build()
                )
                .build();
        build.getBizData().getTokenVO().setRefreshToken(refreshToken);
        return build;
    }
}
