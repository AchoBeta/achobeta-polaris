package com.achobeta.domain.announce.model.entity;

import lombok.*;

import java.util.List;
/**
 * @author huangwenxing
 * @description 用户公告实体
 * @data 2024/11/10
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnnounceEntity {
    /**用户id*/
    private String userId;
    /**用户公告*/
    private List<UserAnnounceEntity> userAnnounceEntities;
}
