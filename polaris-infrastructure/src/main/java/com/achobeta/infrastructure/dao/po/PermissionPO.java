package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 权限持久化对象
 * @date 2024/11/22
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionPO {

    private Long id;
    private String permissionId;
    private String permissionName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
