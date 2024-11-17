package com.achobeta.api.dto.user;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

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
public class ModifyUserInfoRequestDTO implements Serializable {

    @NotBlank(message = "用户业务id不能为空")
    @FieldDesc(name = "用户业务id")
    private String userId;

    @FieldDesc(name = "用户名")
    @Size(max = 20, message = "用户名长度不能超过20")
    private String userName;

    @FieldDesc(name = "性别")
    private Byte gender;

    @FieldDesc(name = "身份证号")
    @Pattern(regexp = "^$|^(?:\\d{15}|\\d{17}(\\d|X))$", message = "身份证号格式不正确")
    private String idCard;

    @Email(message = "邮箱格式不正确")
    @FieldDesc(name = "邮箱")
    private String email;

    @Min(value = 1970, message = "年级不合理")
    @Max(value = 2050, message = "年级不合理")
    @FieldDesc(name = "年级")
    private Integer grade;

    @FieldDesc(name = "专业")
    private String major;

    @Pattern(regexp = "^$|\\d{11,13}", message = "学号必须为空或为11到13位数字")
    @FieldDesc(name = "学号")
    private String studentId;

    @FieldDesc(name = "实习/就职经历")
    private String experience;

    @FieldDesc(name = "现状")
    private String currentStatus;
}
