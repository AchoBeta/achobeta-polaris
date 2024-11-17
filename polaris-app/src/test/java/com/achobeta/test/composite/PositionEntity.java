package com.achobeta.test.composite;

import com.achobeta.types.annotation.FieldDesc;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chensongmin
 * @description
 * @date 2024/11/15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity {
//
//    @FieldDesc(name = "主键ID")
//    private String id;

    @NotBlank
    @FieldDesc(name = "positionId")
    private String pid;

    @NotBlank
    @FieldDesc(name = "节点名称")
    private String nodeName;

    @NotBlank
    @FieldDesc(name = "节点类型 1.团队成员 2.团队岗位")
    private Integer nodeType;

    @NotBlank
    @FieldDesc(name = "团队ID")
    private String teamId;

    public boolean isRoot() {
        return this.teamId.equals(this.pid);
    }

}
