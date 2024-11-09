package com.achobeta.trigger.http;

import com.achobeta.api.IUserService;
import com.achobeta.api.dto.user.ModifyUserInfoRequestDTO;
import com.achobeta.api.dto.user.ModifyUserInfoResponseDTO;
import com.achobeta.api.dto.user.UserInfoRequestDTO;
import com.achobeta.api.dto.user.UserInfoResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IModifyUserInfoService;
import com.achobeta.domain.user.service.IUserInfoService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangzhiyao
 * @create 2024/11/6
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController implements IUserService {

    private final IUserInfoService userInfoService;

    private final IModifyUserInfoService modifyUserInfoService;

    /**
     * 个人中心信息页面接口
     * @param userInfoRequestDTO
     * @author yangzhiyao
     * @date 2024/11/6
     */
    @GetMapping("/center")
    @Override
    public Response<UserInfoResponseDTO> center(@RequestBody UserInfoRequestDTO userInfoRequestDTO) {
        try {
            log.info("用户访问个人中心信息页面系统开始，userId:{}", userInfoRequestDTO.getUserId());

            UserEntity userEntity = userInfoService.getUserInfo(userInfoRequestDTO.getUserId());
            log.info("用户访问个人中心信息页面系统结束，userId:{}", userInfoRequestDTO.getUserId());

            return Response.<UserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(UserInfoResponseDTO.builder()
                            .userId(userEntity.getUserId())
                            .userName(userEntity.getUserName())
                            .phone(userEntity.getPhone())
                            .gender(userEntity.getGender())
                            .idCard(userEntity.getIdCard())
                            .email(userEntity.getEmail())
                            .grade(userEntity.getGrade())
                            .major(userEntity.getMajor())
                            .studentId(userEntity.getStudentId())
                            .experience(userEntity.getExperience())
                            .currentStatus(userEntity.getCurrentStatus())
                            .entryTime(userEntity.getEntryTime())
                            .likeCount(userEntity.getLikeCount())
                            .liked(userEntity.getLiked())
                            .positions(userEntity.getPositions())
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("用户访问个人中心信息页面系统失败！userId:{}",
                    userInfoRequestDTO.getUserId(), e);
            return Response.<UserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }

    /**
     * 修改个人信息接口
     * @param modifyUserInfoRequestDTO
     * @author yangzhiyao
     * @date 2024/11/9
     */
    @PostMapping("/modify")
    @Override
    public Response<ModifyUserInfoResponseDTO> modify(@RequestBody ModifyUserInfoRequestDTO modifyUserInfoRequestDTO) {
        try {
            log.info("用户访问修改个人信息系统开始，userId:{}", modifyUserInfoRequestDTO.getUserId());
            modifyUserInfoService.modifyUserInfo(modifyUserInfoRequestDTO.getUserId(),
                    modifyUserInfoRequestDTO.getUserName(),
                    modifyUserInfoRequestDTO.getPhone(),
                    modifyUserInfoRequestDTO.getGender(),
                    modifyUserInfoRequestDTO.getIdCard(),
                    modifyUserInfoRequestDTO.getEmail(),
                    modifyUserInfoRequestDTO.getGrade(),
                    modifyUserInfoRequestDTO.getMajor(),
                    modifyUserInfoRequestDTO.getStudentId(),
                    modifyUserInfoRequestDTO.getExperience(),
                    modifyUserInfoRequestDTO.getCurrentStatus(),
                    modifyUserInfoRequestDTO.getEntryTime());
            log.info("用户访问修改个人信息系统结束，userId:{}", modifyUserInfoRequestDTO.getUserId());

            return Response.<ModifyUserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(ModifyUserInfoResponseDTO.builder()
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("用户访问修改个人信息系统失败！userId:{}",
                    modifyUserInfoRequestDTO.getUserId(), e);
            return Response.<ModifyUserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.NO_PERMISSIONS.getCode())
                    .info(Constants.ResponseCode.NO_PERMISSIONS.getInfo())
                    .build();
        }
    }
}
