package com.achobeta.api;

import com.achobeta.api.dto.device.GetUserDeviceRequestDTO;
import com.achobeta.api.dto.device.GetUserDeviceResponseDTO;
import com.achobeta.api.response.Response;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
public interface IDeviceService {
    Response<GetUserDeviceResponseDTO> getDevices(GetUserDeviceRequestDTO getUserDeviceRequestDTO);
}
