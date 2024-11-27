package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description 公告用户持久化对象
 * @data 2024/11/10
 * */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnounceReciverPO {
    /**主键id*/
    private long id;
    /**用户业务id*/
    private String userId;
    /**公告业务id*/
    private String announceId;
    /**是否已读*/
    private boolean read;
    /**创建时间*/
    private LocalDateTime createTime;
    /**更新时间*/
    private LocalDateTime updateTime;
}
