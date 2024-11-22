package com.achobeta.domain.auth.model.entity;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 权限实体对象
 * @date 2024/11/22
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity {

    @FieldDesc(name = "权限id")
    private String permissionId;

    @FieldDesc(name = "权限名称")
    private String permissionName;

    @FieldDesc(name = "创建时间")
    private LocalDateTime createTime;

    @FieldDesc(name = "更新时间")
    private LocalDateTime updateTime;

}
