package com.achobeta.infrastructure.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/11 20:10
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DevicePO {

    /*
     * 设备业务id
     */
    private String deviceId;
    /*
     * 设备名称
     */
    private String deviceName;
    /*
     * 用户业务id
     */
    private String userId;
    /*
     * 设备ip地址
     */
    private String ip;
    /*
     * 创建时间
     */
    private LocalDateTime createTime;
    /*
     *更新时间
     */
    private LocalDateTime updateTime;
    /*
     * 是否自动登录
     */
    private Integer isCancel;
}
