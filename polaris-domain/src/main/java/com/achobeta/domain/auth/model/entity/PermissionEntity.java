package com.achobeta.domain.auth.model.entity;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

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

    @FieldDesc(name = "最高级权限")
    private Integer SUPER = 0;

    @FieldDesc(name = "成员相关权限")
    private Integer MEMBER = 0;

    @FieldDesc(name = "组织架构相关权限")
    private Integer STRUCTURE = 0;

    @FieldDesc(name = "用户相关权限")
    private Integer USER = 0;

    @FieldDesc(name = "权限相关权限")
    private Integer AUTH = 0;

    @FieldDesc(name = "修改成员信息")
    private Integer MEMBER_MODIFY = 0;

    @FieldDesc(name = "查看成员列表")
    private Integer MEMBER_LIST = 0;

    @FieldDesc(name = "添加成员")
    private Integer MEMBER_ADD = 0;

    @FieldDesc(name = "删除成员")
    private Integer MEMBER_DELETE = 0;

    @FieldDesc(name = "查看成员信息详情")
    private Integer MEMBER_DETAIL = 0;

    @FieldDesc(name = "修改团队组织架构")
    private Integer STRUCTURE_MODIFY = 0;

    @FieldDesc(name = "查看团队组织架构")
    private Integer STRUCTURE_VIEW = 0;

    @FieldDesc(name = "添加团队")
    private Integer TEAM_ADD = 0;

    @FieldDesc(name = "删除团队")
    private Integer TEAM_DELETE = 0;

    @FieldDesc(name = "查看角色列表")
    private Integer ROLE_LIST = 0;
}
