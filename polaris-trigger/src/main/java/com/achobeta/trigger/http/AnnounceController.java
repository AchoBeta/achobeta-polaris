package com.achobeta.trigger.http;

import com.achobeta.api.dto.announce.*;
import com.achobeta.domain.announce.model.valobj.UserAnnounceVO;
import com.achobeta.domain.announce.service.IAnnounceService;
import com.achobeta.types.Response;
import com.achobeta.types.common.Constants;
import com.achobeta.types.constraint.LoginVerification;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/announce/")
@RequiredArgsConstructor
public class AnnounceController implements com.achobeta.api.IAnnounceService {
    private final IAnnounceService service;

    /**
     *
     * @param getUserAnnounceRequestDTO
     * @return
     */
    @GetMapping("/getUserAnnounce")
//    @LoginVerification
    @Override
    public Response<GetUserAnnounceResponseDTO> getUserAnnounce(@Valid GetUserAnnounceRequestDTO getUserAnnounceRequestDTO) {
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
    @LoginVerification
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
                    readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId(), e.getInfo(),e);
            return Response.builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        }
        catch (Exception e){
            log.error("用户访问读公告失败，userId:{} announceId:{}",
                    readAnnounceRequestDTO.getUserId(),readAnnounceRequestDTO.getAnnounceId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }

    @Override
    @LoginVerification
    @GetMapping("/getAnnounceCount")
    public Response<GetUserAnnounceCountResponseDTO> getUserAnnounceCount(@Valid GetUserAnnounceCountRequestDTO getUserAnnounceCountRequestDTO) {
        try {
            log.info("用户获取公告数量开始，userId:{}",
                    getUserAnnounceCountRequestDTO.getUserId());
            Integer count = service.queryAnnounceCountByUserId(getUserAnnounceCountRequestDTO.getUserId());
            log.info("用户获取公告数量结束");
            return Response.SYSTEM_SUCCESS(GetUserAnnounceCountResponseDTO.builder()
                            .count(count)
                    .build());
        }catch (Exception e){
            log.error("用户获取公告数量失败，userId:{} ",
                    getUserAnnounceCountRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }

    @Override
    @LoginVerification
    @PostMapping("/readAllAnnounce")
    public Response readAllAnnounce(@Valid @RequestBody ReadAllAnnounceRequestDTO readAllAnnounceRequestDTO) {
        try {
            log.info("用户读取所有公告开始，userId:{}",
                    readAllAnnounceRequestDTO.getUserId());
            service.readAllAnnounce(readAllAnnounceRequestDTO.getUserId());
            log.info("用户读取所有公告结束");
            return Response.SYSTEM_SUCCESS();
        }catch (AppException e){
            log.error("用户读取所有公告失败，userId:{} 已知异常e:{}",
                    readAllAnnounceRequestDTO.getUserId(), e.getInfo(),e);
            return Response.builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        }
        catch (Exception e){
            log.error("用户读取所有公告失败，userId:{} ",
                    readAllAnnounceRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
