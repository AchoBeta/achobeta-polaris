package com.achobeta.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @NotBlank(message = "用户业务id不能为空")
    private String userId;
    /**
     * 用户名
     */
    private String userName;
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
    @Email(message = "邮箱格式不正确")
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
    @Size(min=13, max=13, message="学号长度必须为13位")
    private String studentId;
    /**
     * 用户实习/就职经历
     */
    private String experience;
    /**
     * 用户现状
     */
    private String currentStatus;
}
