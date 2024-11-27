package com.achobeta.domain.user.model.entity;

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
 * @description 用户实体对象,，时间类不加那几个注解存不了Redis
 * @date 2024/11/5
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
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
    private Integer gender;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime entryTime;
    /**
     * 用户所获得赞数量
     */
    private Integer likeCount;
    /**
     * 用户点赞状态
     */
    private Boolean liked;
    /**
     * 用户所属职位/分组
     */
    private List<List<String>> positions;
    /**
     * 用户所属职位列表
     * 和上面的positions差不多，只是为了方便前端显示和逻辑处理多做一个
     */
    private List<String> positionList;

    @FieldDesc(name = "赋予的角色id")
    private List<String> roles;
}
