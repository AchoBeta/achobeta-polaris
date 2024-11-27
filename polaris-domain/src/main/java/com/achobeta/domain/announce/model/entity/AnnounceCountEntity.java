package com.achobeta.domain.announce.model.entity;

import lombok.*;

/**
 * @author huangwenxing
 * @description 公告数量
 * @data 2024/11/17
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceCountEntity {
    /**用户id*/
    private String userId;
    /**公告数量*/
    private Integer count;
}
