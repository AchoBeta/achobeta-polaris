package com.achobeta.trigger.http;

import com.achobeta.api.IUserService;
import com.achobeta.api.dto.user.QueryUserInfoRequestDTO;
import com.achobeta.api.dto.user.QueryUserInfoResponseDTO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IUserInfoService;
import com.achobeta.types.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/${app.config.api-version}/read/")
@RequiredArgsConstructor
public class UserController implements IUserService {

    private final IUserInfoService userInfoService;

    /**
     * 个人中心信息页面接口
     * @param queryUserInfoRequestDTO
     * @author yangzhiyao
     * @date 2024/11/6
     */
    @GetMapping("/center")
    @Override
    public Response<QueryUserInfoResponseDTO> queryUserCenterInfo(@Valid @RequestBody QueryUserInfoRequestDTO queryUserInfoRequestDTO) {
        try {
            log.info("用户访问个人中心信息页面系统开始，userId:{}", queryUserInfoRequestDTO.getUserId());

            UserEntity userEntity = userInfoService.getUserInfo(queryUserInfoRequestDTO.getUserId());
            log.info("用户访问个人中心信息页面系统结束，userId:{}", queryUserInfoRequestDTO.getUserId());

            return Response.SYSTEM_SUCCESS(QueryUserInfoResponseDTO.builder()
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
                    .build());
        } catch (Exception e) {
            log.error("用户访问个人中心信息页面系统失败！userId:{}",
                    queryUserInfoRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR();
        }
    }

}
