package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 团队成员修改后的个人信息请求DTO
 * @date 2024/11/17
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMemberInfoRequestDTO implements Serializable {

    @NotBlank(message = "团队ID不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;

    @NotBlank(message = "被修改的成员的用户ID不能为空")
    @FieldDesc(name = "被修改的成员的用户ID")
    private String memberId;

    @NotBlank(message = "用户业务id不能为空")
    @FieldDesc(name = "发起操作的用户ID")
    private String userId;

    @FieldDesc(name = "用户名")
    private String userName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号格式不正确")
    @FieldDesc(name = "手机号")
    private String phone;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @PastOrPresent(message = "加入时间必须是过去时间或现在")
    @FieldDesc(name = "加入时间")
    private LocalDate entryTime;

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

    @FieldDesc(name = "赋予的角色id")
    private List<String> roles;

    @FieldDesc(name = "所属团队/职位")
    private List<String> positions;
}