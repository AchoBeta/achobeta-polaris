package com.achobeta.api;


import com.achobeta.api.dto.GetCodeRequestDTO;
import com.achobeta.api.dto.GetCodeResponseDTO;
import com.achobeta.api.response.Response;

/**
 * @Author: 严豪哲
 * @Description:获取验证码api定义
 * @Date: 2024/11/5 21:11
 * @Version: 1.0
 */

public interface IGetCodeService {

    Response<GetCodeResponseDTO> getCode(GetCodeRequestDTO getCodeRequestDTO);
}
