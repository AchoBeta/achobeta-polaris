package com.achobeta.trigger.http;

import com.achobeta.api.dto.device.GetUserDeviceRequestDTO;
import com.achobeta.api.dto.device.GetUserDeviceResponseDTO;
import com.achobeta.domain.device.model.valobj.UserCommonDevicesVO;
import com.achobeta.domain.device.service.IDeviceService;
import com.achobeta.types.Response;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author huangwenxing
 * @date 2024/11/9
 */
@Slf4j
@Validated
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
    public Response<GetUserDeviceResponseDTO> getDevices(@Valid  GetUserDeviceRequestDTO getUserDeviceRequestDTO) {
        try {
            log.info("用户访问设备渲染页面系统开始，userId:{} deviceId:{} limit:{} lastDeviceId:{}",
                    getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(), getUserDeviceRequestDTO.getLastDeviceId());

            UserCommonDevicesVO userCommonDevicesVO = deviceTextService.
                    queryCommonUseDevicesById(getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(), getUserDeviceRequestDTO.getLastDeviceId());

            log.info("用户访问设备渲染页面系统结束，deviceEntities:{} more:{}",userCommonDevicesVO.getDeviceEntities(),userCommonDevicesVO.isMore() );

            return  Response.SYSTEM_SUCCESS(GetUserDeviceResponseDTO.builder()
                    .userCommonUseDevices(userCommonDevicesVO.getDeviceEntities())
                    .more(userCommonDevicesVO.isMore())
                    .build());
        }catch (AppException e){
            log.error("用户访问设备渲染页面系统失败!userId:{} deviceId:{} limit:{} lastDeviceId:{}",
                    getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(),getUserDeviceRequestDTO.getLastDeviceId(), e);
            return Response.CUSTOMIZE_ERROR(GlobalServiceStatusCode.PARAM_NOT_VALID);
        }
        catch (Exception e) {
            log.error("用户访问设备渲染页面系统失败！userId:{} deviceId:{} limit:{} lastDeviceId:{}",
                    getUserDeviceRequestDTO.getUserId(), getUserDeviceRequestDTO.getDeviceId(), getUserDeviceRequestDTO.getLimit(),getUserDeviceRequestDTO.getLastDeviceId(), e);
            return  Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
