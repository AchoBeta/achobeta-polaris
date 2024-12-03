package com.achobeta.trigger.http;

import com.achobeta.api.dto.like.LikeRequestDTO;
import com.achobeta.domain.like.service.ILikeService;
import com.achobeta.types.Response;
import com.achobeta.types.common.Constants;
import com.achobeta.types.constraint.LoginVerification;
import com.achobeta.types.constraint.SelfPermissionVerification;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author huangwenxing
 * @date 2024/11/17
 */
@Slf4j
@RestController()
@Validated
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/user")
@RequiredArgsConstructor
public class LikeController implements com.achobeta.api.ILikeService {
    private final ILikeService service;

    @Override
    @LoginVerification
    @SelfPermissionVerification
    @PostMapping("/like")
    public Response like(@Valid @RequestBody LikeRequestDTO likeRequestDTO) {
        try {
            log.info("点赞系统开始，fromId:{} toId:{} liked:{}",
                    likeRequestDTO.getUserId(),likeRequestDTO.getToId(),likeRequestDTO.isLiked());
            service.Like(likeRequestDTO.getUserId(),likeRequestDTO.getToId(),likeRequestDTO.isLiked());
            log.info("点赞系统结束，fromId:{} toId:{} liked:{}",
                    likeRequestDTO.getUserId(),likeRequestDTO.getToId(),likeRequestDTO.isLiked());
            return Response.SYSTEM_SUCCESS();
        } catch (AppException e){
            log.error("fromId:{} toId:{} liked:{} 已知异常e:{}",
                    likeRequestDTO.getUserId(),likeRequestDTO.getToId(),likeRequestDTO.isLiked(), e.getInfo(), e);
            return Response.builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        }
        catch (Exception e) {
            log.error("fromId:{} toId:{} liked:{}",
                    likeRequestDTO.getUserId(),likeRequestDTO.getToId(),likeRequestDTO.isLiked(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }
}
