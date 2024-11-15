package com.achobeta.domain.announce.model.entity;

import lombok.*;

/**
 * @author huangwenxing
 * @description 读公告实体
 * @data 2024/11/13
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadAnnounceEntity {
    /**用户id*/
    private String userId;
    /**公告id*/
    private String AnnounceId;
}
