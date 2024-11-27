package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 添加团队成员请求数据对象
 * @date 2024/11/19
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddMemberRequestDTO implements Serializable {

    @NotBlank(message = "团队ID不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;

    @NotBlank(message = "用户业务id不能为空")
    @FieldDesc(name = "发起操作的用户ID")
    private String userId;

    @FieldDesc(name = "用户名")
    private String userName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号格式不正确")
    @FieldDesc(name = "手机号")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Past(message = "加入时间必须是过去时间")
    @FieldDesc(name = "加入时间")
    private LocalDateTime entryTime;

    @FieldDesc(name = "性别")
    private Integer gender;

    @FieldDesc(name = "身份证号")
    @Pattern(regexp = "^$|^\\d{18}$|^\\d{15}$", message = "身份证号格式不正确")
    private String idCard;

    @Email(message = "邮箱格式不正确")
    @FieldDesc(name = "邮箱")
    private String email;

    @FieldDesc(name = "年级")
    private Integer grade;

    @FieldDesc(name = "专业")
    private String major;

    @Pattern(regexp = "^$|\\d{13}", message = "学号必须为空或为13位数字")
    @FieldDesc(name = "学号")
    private String studentId;

    @FieldDesc(name = "实习/就职经历")
    private String experience;

    @FieldDesc(name = "现状")
    private String currentStatus;

    @FieldDesc(name ="添加的职位/分组")
    private List<String> positions;
}
