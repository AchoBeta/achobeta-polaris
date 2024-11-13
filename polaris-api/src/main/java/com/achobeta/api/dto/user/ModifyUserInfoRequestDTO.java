package com.achobeta.api.dto.user;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @FieldDesc(name = "用户名")
    private String userName;

    @FieldDesc(name = "性别")
    private Byte gender;

    @FieldDesc(name = "身份证号")
    private String idCard;

    @Email(message = "邮箱格式不正确")
    @FieldDesc(name = "邮箱")
    private String email;

    @FieldDesc(name = "年级")
    private Integer grade;

    @FieldDesc(name = "专业")
    private String major;

    @Size(min=13, max=13, message="学号长度必须为13位")
    @FieldDesc(name = "学号")
    private String studentId;

    @FieldDesc(name = "实习/就职经历")
    private String experience;

    @FieldDesc(name = "现状")
    private String currentStatus;
}
