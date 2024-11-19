package com.achobeta.trigger.http;

import com.achobeta.api.IUserService;
import com.achobeta.api.dto.user.ModifyUserInfoRequestDTO;
import com.achobeta.api.dto.user.ModifyUserInfoResponseDTO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IModifyUserInfoService;
import com.achobeta.types.Response;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yangzhiyao
 * @create 2024/11/6
 */
@Slf4j
@RestController()
@Validated
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/user/")
@RequiredArgsConstructor
public class UserController implements IUserService {

    private final IModifyUserInfoService modifyUserInfoService;

    /**
     * 修改个人信息接口
     * @param modifyUserInfoRequestDTO
     * @author yangzhiyao
     * @date 2024/11/9
     */
    @PutMapping("/modify")
    @Override
    public Response<ModifyUserInfoResponseDTO> modifyUserInfo(@Valid @RequestBody ModifyUserInfoRequestDTO modifyUserInfoRequestDTO) {
        try {
            log.info("用户访问修改个人信息系统开始，userId:{}", modifyUserInfoRequestDTO.getUserId());
             UserEntity userEntity = UserEntity.builder()
                          .userId(modifyUserInfoRequestDTO.getUserId())
                          .userName(modifyUserInfoRequestDTO.getUserName())
                          .gender(modifyUserInfoRequestDTO.getGender())
                          .idCard(modifyUserInfoRequestDTO.getIdCard())
                          .email(modifyUserInfoRequestDTO.getEmail())
                          .grade(modifyUserInfoRequestDTO.getGrade())
                          .major(modifyUserInfoRequestDTO.getMajor())
                          .studentId(modifyUserInfoRequestDTO.getStudentId())
                          .experience(modifyUserInfoRequestDTO.getExperience())
                          .currentStatus(modifyUserInfoRequestDTO.getCurrentStatus())
                          .build();
            modifyUserInfoService.modifyUserInfo(userEntity);
            log.info("用户访问修改个人信息系统结束，userId:{}", modifyUserInfoRequestDTO.getUserId());
            return Response.SYSTEM_SUCCESS(ModifyUserInfoResponseDTO.builder()
                    .userId(modifyUserInfoRequestDTO.getUserId())
                    .userName(modifyUserInfoRequestDTO.getUserName())
                    .gender(modifyUserInfoRequestDTO.getGender())
                    .idCard(modifyUserInfoRequestDTO.getIdCard())
                    .email(modifyUserInfoRequestDTO.getEmail())
                    .grade(modifyUserInfoRequestDTO.getGrade())
                    .major(modifyUserInfoRequestDTO.getMajor())
                    .studentId(modifyUserInfoRequestDTO.getStudentId())
                    .experience(modifyUserInfoRequestDTO.getExperience())
                    .currentStatus(modifyUserInfoRequestDTO.getCurrentStatus())
                    .build());
        }  catch (AppException e) {
            log.error("用户访问修改个人信息系统失败！userId:{}",
                    modifyUserInfoRequestDTO.getUserId(), e);
            return Response.<ModifyUserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Integer.valueOf(e.getCode()))
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("用户访问修改个人信息系统失败！userId:{}",
                    modifyUserInfoRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR();
        }
    }
}
