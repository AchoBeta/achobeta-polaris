package com.achobeta.api.dto.team;

import com.achobeta.types.annotation.FieldDesc;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author yangzhiyao
 * @description 查询队员列表请求DTO
 * @date 2024/11/17
 */
@Getter
public class RequestMemberListDTO {

    @NotBlank(message = "团队ID不能为空")
    @FieldDesc(name = "团队ID")
    private String teamId;

    @FieldDesc(name = "最后一条记录的ID")
    private Long lastId;

    @Min(value = 1, message = "每页记录数不能小于1")
    @FieldDesc(name = "每页记录数")
    private Integer limit;
}
