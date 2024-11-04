package com.achobeta.api;

import com.achobeta.api.dto.ReadRequestDTO;
import com.achobeta.api.response.Response;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
public interface IReadService {

    Response<String> read(ReadRequestDTO readRequestDTO);

}
