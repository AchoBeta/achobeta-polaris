package com.achobeta.trigger.http;

import com.achobeta.api.dto.announce.GetUserAnnounceRequestDTO;
import com.achobeta.api.dto.announce.GetUserAnnounceResponseDTO;
import com.achobeta.api.dto.announce.ReadAnnounceRequestDTO;
import com.achobeta.domain.announce.model.valobj.UserAnnounceVO;
import com.achobeta.domain.announce.service.IAnnounceService;
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
 * @date 2024/11/11
 */
@Slf4j
@RestController()
@Validated
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/announce/")
@RequiredArgsConstructor
public class AnnounceController implements com.achobeta.api.IAnnounceService {
    private final IAnnounceService service;

    /**
     *
     * @param getUserAnnounceRequestDTO
     * @return
     */
    @PostMapping("/getUserAnnounce")
    @Override
    public Response<GetUserAnnounceResponseDTO> getUserAnnounce(@Valid @RequestBody GetUserAnnounceRequestDTO getUserAnnounceRequestDTO) {
        try {
            log.info("用户访问公告渲染系统开始，userId:{} limit:{} lastAnnounceId:{}",
                    getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId());

            UserAnnounceVO userAnnounceVO = service.queryAnnouncesByUserId(getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId());


            log.info("用户访问公告渲染系统结束，announceEntities:{} more:{}",userAnnounceVO.getAnnounceEntities(),userAnnounceVO.isMore() );

            return Response.SYSTEM_SUCCESS(GetUserAnnounceResponseDTO.builder()
                    .userAnnounce(userAnnounceVO.getAnnounceEntities())
                    .more(userAnnounceVO.isMore())
                    .build());
        } catch (Exception e) {
            log.error("用户访问公告渲染系统失败！userId:{} limit:{} lastAnnounceId:{}",
                    getUserAnnounceRequestDTO.getUserId(), getUserAnnounceRequestDTO.getLimit(), getUserAnnounceRequestDTO.getLastAnnounceId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }

    @Override
    @PostMapping("/readUserAnnounce")
    public Response readAnnounce(@Valid @RequestBody ReadAnnounceRequestDTO readAnnounceRequestDTO) {
        try {
            log.info("用户访问读公告开始，userId:{} announceId:{}",
                   readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId());
            service.readAnnounce(readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId());
            log.info("用户访问读公告结束");
            return Response.SYSTEM_SUCCESS();
        }catch (AppException e){
            log.error("用户访问读公告失败，userId:{} announceId:{} 已知异常e:{}",
                    readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId(), e.getMessage(),e);
            return Response.CUSTOMIZE_ERROR(GlobalServiceStatusCode.PARAM_NOT_VALID);
        }
        catch (Exception e){
            log.error("用户访问读公告失败，userId:{} announceId:{}",
                    readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
