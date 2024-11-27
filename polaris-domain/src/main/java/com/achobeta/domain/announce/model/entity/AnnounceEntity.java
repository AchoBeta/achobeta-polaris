package com.achobeta.domain.announce.model.entity;

import lombok.*;
/**
 * @author huangwenxing
 * @description 公告实体
 * @data 2024/11/10
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceEntity {
    /**公告标题*/
    private String title;
    /**公告内容*/
    private String body;
    /**是否已读*/
    private boolean read;
    /**公告类型*/
    private int type;
    /**公告id*/
    private String announceId;

}
