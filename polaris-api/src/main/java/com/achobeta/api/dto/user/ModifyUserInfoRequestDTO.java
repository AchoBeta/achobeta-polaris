package com.achobeta.api.dto.user;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 用户修改后的个人信息请求DTO
 * @date 2024/11/9
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserInfoRequestDTO {
    /**
     * 用户业务id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户性别
     */
    private Byte gender;
    /**
     * 用户身份证号
     */
    private String idCard;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户年级
     */
    private Integer grade;
    /**
     * 用户专业
     */
    private String major;
    /**
     * 用户学号
     */
    private String studentId;
    /**
     * 用户实习/就职经历
     */
    private String experience;
    /**
     * 用户现状
     */
    private String currentStatus;
    /**
     * 用户加入时间
     */
    private LocalDateTime entryTime;
}
