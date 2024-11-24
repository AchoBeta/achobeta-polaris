package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangzhiyao
 * @description 团队架构请求参数对象
 * @date 2024/11/7
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryStructureRequestDTO implements Serializable {

    @NotBlank(message = "userId不能为空")
    @FieldDesc(name = "用户ID")
    private String userId;

    @NotBlank(message = "teamId不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;
}
