package com.achobeta.api.dto.team;

import com.achobeta.types.annotation.FieldDesc;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 查询队员列表响应DTO
 * @date 2024/11/17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMemberListDTO {

    @FieldDesc(name = "成员业务id")
    private String userId;

    @FieldDesc(name = "成员姓名")
    private String userName;

    @FieldDesc(name = "成员手机号")
    private String phone;

    @FieldDesc(name = "成员性别")
    private Byte gender;

    @FieldDesc(name = "成员身份证号")
    private String idCard;

    @FieldDesc(name = "成员邮箱")
    private String email;

    @FieldDesc(name = "成员年级")
    private Integer grade;

    @FieldDesc(name = "成员专业")
    private String major;

    @FieldDesc(name = "成员学号")
    private String studentId;

    @FieldDesc(name = "成员实习/就职经历")
    private String experience;
    
    @FieldDesc(name = "成员现状")
    private String currentStatus;
    
    @FieldDesc(name = "成员加入时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime entryTime;
    
    @FieldDesc(name = "成员获得赞数量")
    private Integer likeCount;
    
    @FieldDesc(name = "成员点赞状态")
    private Boolean liked;
    
    @FieldDesc(name = "成员所属职位/分组")
    private List<List<String>> positions;

    @FieldDesc(name = "实际成员列表")
    private Object members;
}
