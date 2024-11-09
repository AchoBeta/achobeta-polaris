package com.achobeta.domain.user.model.entity;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

/**
 * @author yangzhiyao
 * @description 用户实体对象
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
    private LocalTime entryTime;
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
    private List<String> positions;
}
