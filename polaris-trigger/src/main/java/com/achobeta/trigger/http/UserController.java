package com.achobeta.trigger.http;

import com.achobeta.api.IUserService;
import com.achobeta.api.dto.user.UserInfoRequestDTO;
import com.achobeta.api.dto.user.UserInfoResponseDTO;
import com.achobeta.api.response.Response;
import com.achobeta.domain.user.model.valobj.UserInfoVO;
import com.achobeta.domain.user.service.IUserInfoService;
import com.achobeta.types.common.Constants;
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

    private final IUserInfoService userService;

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

            UserInfoVO userInfoVO = userService.getUserInfo(userInfoRequestDTO.getUserId());
            log.info("用户访问个人中心信息页面系统结束，userId:{}", userInfoRequestDTO.getUserId());

            return Response.<UserInfoResponseDTO>builder()
                    .traceId(MDC.get(Constants.TRACE_ID))
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(UserInfoResponseDTO.builder()
                            .userId(userInfoVO.getUserId())
                            .userName(userInfoVO.getUserName())
                            .phone(userInfoVO.getPhone())
                            .gender(userInfoVO.getGender())
                            .idCard(userInfoVO.getIdCard())
                            .email(userInfoVO.getEmail())
                            .grade(userInfoVO.getGrade())
                            .major(userInfoVO.getMajor())
                            .studentId(userInfoVO.getStudentId())
                            .experience(userInfoVO.getExperience())
                            .currentStatus(userInfoVO.getCurrentStatus())
                            .entryTime(userInfoVO.getEntryTime())
                            .likeCount(userInfoVO.getLikeCount())
                            .liked(userInfoVO.getLiked())
                            .positions(userInfoVO.getPositions())
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
}
