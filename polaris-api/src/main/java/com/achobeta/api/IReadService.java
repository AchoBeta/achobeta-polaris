package com.achobeta.api;

import com.achobeta.api.dto.RenderRequestDTO;
import com.achobeta.api.dto.RenderResponseDTO;
import com.achobeta.api.response.Response;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
public interface IReadService {

//    Response<String> read(ReadRequestDTO readRequestDTO);

    Response<RenderResponseDTO> render(RenderRequestDTO readRequestDTO);

}
