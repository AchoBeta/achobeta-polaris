package com.achobeta.domain.like.model;

import lombok.*;

/**
 * @author huangwenxing
 * @description 点赞实体
 * @data 2024/11/17
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity {
    /**点赞人id*/
    private String fromId;
    /**获赞人id*/
    private String toId;
    /**点赞状态*/
    private boolean liked;
}
