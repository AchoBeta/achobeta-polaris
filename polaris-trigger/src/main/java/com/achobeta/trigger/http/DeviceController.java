package com.achobeta.trigger.http;

import com.achobeta.api.dto.device.GetUserDeviceRequestDTO;
import com.achobeta.api.dto.device.GetUserDeviceResponseDTO;
import com.achobeta.domain.device.model.valobj.UserCommonDevicesVO;
import com.achobeta.domain.device.service.IDeviceService;
import com.achobeta.types.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/device/")
@RequiredArgsConstructor
public class DeviceController implements com.achobeta.api.IDeviceService {
    private final IDeviceService deviceTextService;
    /**
     *
     * @param getUserDeviceRequestDTO
     * @return
     */
    @GetMapping("/getDevices")
    @Override
    public Response<GetUserDeviceResponseDTO> getDevices(GetUserDeviceRequestDTO getUserDeviceRequestDTO) {
        try {
            log.info("用户访问文本渲染系统开始，userId:{} deviceId:{} limit:{} lastDeviceId:{}",
                    getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(), getUserDeviceRequestDTO.getLastDeviceId());

            UserCommonDevicesVO userCommonDevicesVO = deviceTextService.
                    queryCommonUseDevicesById(getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(), getUserDeviceRequestDTO.getLastDeviceId());

            log.info("用户访问文本渲染系统结束，deviceEntities:{} more:{}",userCommonDevicesVO.getDeviceEntities(),userCommonDevicesVO.isMore() );

            return  Response.SYSTEM_SUCCESS(GetUserDeviceResponseDTO.builder()
                    .userCommonUseDevices(userCommonDevicesVO.getDeviceEntities())
                    .more(userCommonDevicesVO.isMore())
                    .build());
        } catch (Exception e) {
            log.error("用户访问文本渲染系统失败！userId:{} deviceId:{} limit:{}",
                    getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(), e);
            return  Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
