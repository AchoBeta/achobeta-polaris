package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author huangwenxing
 * @description 公告持久化对象
 * @data 2024/11/10
 * */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncePO {
    /**主键id*/
    private long id;
    /**公告业务id*/
    private String announceId;
    /**标题*/
    private String title;
    /**公告内容*/
    private String body;
    /**目标群体id,0为全体，其他数字为用户id*/
    private String targetId;
    /**公告类型0为全体，其他数字为团队id*/
    private Integer type;
    /**创建时间*/
    private LocalDateTime createTime;
    /**更新时间*/
    private LocalDateTime updateTime;
}
