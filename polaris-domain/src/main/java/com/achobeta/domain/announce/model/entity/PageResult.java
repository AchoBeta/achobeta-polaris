package com.achobeta.domain.announce.model.entity;

import lombok.*;

/**
 * @author huangwenxing
 * @description 单页数据实体
 * @data 2024/11/10
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult {
    /**单页长度*/
    private int limit;
    /**单页最后一个公告id*/
    private String lastAnnounceId;
}
