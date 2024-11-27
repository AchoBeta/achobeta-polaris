package com.achobeta.api;

import com.achobeta.api.dto.LogoutRequestDTO;
import com.achobeta.api.dto.LogoutResponseDTO;
import com.achobeta.types.Response;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: 严豪哲
 * @Description: 登出接口
 * @Date: 2024/11/19 19:03
 * @Version: 1.0
 */
public interface ILogoutService {

    /**
     * 登出
     * @return 登出响应体
     */
    Response<LogoutResponseDTO> logout(@Valid @RequestBody LogoutRequestDTO logoutRequestDTO, HttpServletRequest request, HttpServletResponse response);

}
