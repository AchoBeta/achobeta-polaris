package com.achobeta.api;

import com.achobeta.api.dto.device.GetDevicesResponseDTO;
import com.achobeta.api.dto.device.GetDevicesRequestDTO;
import com.achobeta.api.response.Response;

import java.util.List;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
public interface IDeviceService {
    Response<List<GetDevicesResponseDTO>> getDevices(GetDevicesRequestDTO getDevicesRequestDTO);
}
