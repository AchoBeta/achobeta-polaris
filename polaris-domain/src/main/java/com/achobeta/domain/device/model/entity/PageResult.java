package com.achobeta.domain.device.model.entity;

import lombok.*;
/**
 * @author huangwenxing
 * @description 单页长度实体
 * @data 2024/11/7
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult {
    /**单页长度*/
    private int limit;
    /**单页最后一个设备id*/
    private String lastDeviceId;
}
