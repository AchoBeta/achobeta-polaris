package com.achobeta.infrastructure.gateway;

import com.achobeta.infrastructure.gateway.dto.AuthDTO;
import org.springframework.stereotype.Service;

/**
 * @author chensongmin
 * @description 鉴权 RPC demo
 * @date 2024/11/4
 */
@Service
public class AuthRPC {

    public AuthDTO queryUserAuth(String userId) {
        if ("10001".equals(userId)) {
            return AuthDTO.builder().userId("10001").userName("半糖").department("AchoBeta4.0").isAuth(true).isInWhite(false).build();
        } else if ("10002".equals(userId)) {
            return AuthDTO.builder().userId("10002").userName("红神").department("AchoBeta4.0").isAuth(true).isInWhite(false).build();
        } else if ("10003".equals(userId)) {
            return AuthDTO.builder().userId("10003").userName("马拉圈").department("AchoBeta5.0").isAuth(true).isInWhite(false).build();
        } else if ("10004".equals(userId)) {
            return AuthDTO.builder().userId("10004").userName("马大帅").department("AchoBeta7.0").isAuth(false).isInWhite(false).build();
        } else if ("10009".equals(userId)) {
            // 白名单用户配置
            return AuthDTO.builder().userId("10009").userName("游客").department("").isAuth(false).isInWhite(true).build();
        }
        return AuthDTO.builder().build();
    }

}
