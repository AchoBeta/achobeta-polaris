package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 成员个人信息DTO
 * @date 2024/11/19
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryMemberInfoResponseDTO implements Serializable {

    @FieldDesc(name = "用户ID")
    private String userId;

    @FieldDesc(name = "用户昵称")
    private String userName;

    @FieldDesc(name = "用户手机号")
    private String phone;

    @FieldDesc(name = "用户性别")
    private Integer gender;

    @FieldDesc(name = "身份证号")
    private String idCard;

    @FieldDesc(name = "用户邮箱")
    private String email;

    @FieldDesc(name = "年级")
    private Integer grade;

    @FieldDesc(name = "专业")
    private String major;

    @FieldDesc(name = "学号")
    private String studentId;

    @FieldDesc(name = "用户实习/就职经历")
    private String experience;

    @FieldDesc(name = "用户现状")
    private String currentStatus;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @FieldDesc(name = "用户加入时间")
    private LocalDate entryTime;

    @FieldDesc(name = "用户获赞数量")
    private Integer likeCount;

    @FieldDesc(name = "用户点赞状态")
    private Boolean liked;

    @FieldDesc(name = "用户职位信息")
    private List<List<String>> positions;
}
