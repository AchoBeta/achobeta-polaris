package com.achobeta.trigger.http;

import com.achobeta.api.IDeviceService;
import com.achobeta.api.dto.device.GetDevicesRequestDTO;
import com.achobeta.api.dto.device.GetDevicesResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.device.model.valobj.DeviceVO;
import com.achobeta.domain.device.service.IDeviceTextService;
import com.achobeta.types.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/device/")
@RequiredArgsConstructor
public class DeviceController implements IDeviceService {
    private final IDeviceTextService deviceTextService;
    /**
     *
     * @param getDevicesRequestDTO
     * @return
     */
    @GetMapping("/getDevices")
    @Override
    public Response<List<GetDevicesResponseDTO>> getDevices(GetDevicesRequestDTO getDevicesRequestDTO) {
        try {
            log.info("用户访问文本渲染系统开始，userId:{} deviceId:{} limit:{} lastDeviceId:{}",
                    getDevicesRequestDTO.getUserId(), getDevicesRequestDTO.getDeviceId(), getDevicesRequestDTO.getLimit(), getDevicesRequestDTO.getLastDeviceId());

            List<DeviceVO> deviceVO = deviceTextService.
                    getDeviceVO(getDevicesRequestDTO.getUserId(), getDevicesRequestDTO.getDeviceId(), getDevicesRequestDTO.getLimit(), getDevicesRequestDTO.getLastDeviceId());

            log.info("用户访问文本渲染系统结束，deviceVOS:{}",
                    deviceVO);
            List<GetDevicesResponseDTO> dtoList =   deviceVO.stream().map(deviceVO1 -> GetDevicesResponseDTO.builder()
                    .deviceId(deviceVO1.getDeviceId())
                    .deviceName(deviceVO1.getDeviceName())
                    .lastLoginTime(deviceVO1.getLastLoginTime())
                    .IP(deviceVO1.getIP())
                    .me(deviceVO1.isMe())
                    .build()).collect(Collectors.toList());
            return Response.<List<GetDevicesResponseDTO>>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(dtoList)
                    .build();

        } catch (Exception e) {
            log.error("用户访问文本渲染系统失败！userId:{} deviceId:{} limit:{}",
                    getDevicesRequestDTO.getUserId(), getDevicesRequestDTO.getDeviceId(), getDevicesRequestDTO.getLimit(), e);
            return Response.<List<GetDevicesResponseDTO>>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }
}
