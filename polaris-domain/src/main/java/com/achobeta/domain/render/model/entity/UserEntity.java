package com.achobeta.domain.render.model.entity;

import lombok.*;

/**
 * @author chensongmin
 * @description Render Domain 文本渲染领域的用户实体对象
 * 一般来说都是数据库对应实体的子集
 * 与 ORM 映射实体相比不能带上主键 ID 与实现本领域功能无关的字段
 * @create 2024/11/3
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * 用户业务ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户所在部门
     */
    private String department;

}
