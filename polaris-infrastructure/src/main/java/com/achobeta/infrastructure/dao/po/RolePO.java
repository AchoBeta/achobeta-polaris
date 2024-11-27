package com.achobeta.infrastructure.dao.po;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author yangzhiyao
 * @description 角色持久化对象
 * @date 2024/11/22
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePO {

    private Long id;
    private String roleId;
    private String roleName;
    private String teamId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
