package com.achobeta.trigger.http;

import com.achobeta.api.IUserService;
import com.achobeta.api.dto.user.ModifyUserInfoRequestDTO;
import com.achobeta.api.dto.user.ModifyUserInfoResponseDTO;
import com.achobeta.api.dto.user.QueryUserInfoRequestDTO;
import com.achobeta.api.dto.user.QueryUserInfoResponseDTO;
import com.achobeta.domain.user.model.entity.UserEntity;
import com.achobeta.domain.user.service.IModifyUserInfoService;
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
@CrossOrigin("*")
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController implements IUserService {

    private final IUserInfoService userInfoService;

    private final IModifyUserInfoService modifyUserInfoService;

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

    /**
     * 修改个人信息接口
     * @param modifyUserInfoRequestDTO
     * @author yangzhiyao
     * @date 2024/11/9
     */
    @PostMapping("/modify")
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
        }  catch (Exception e) {
            log.error("用户访问修改个人信息系统失败！userId:{}",
                    modifyUserInfoRequestDTO.getUserId(), e);
            return Response.SERVICE_ERROR();
        }
    }
}
