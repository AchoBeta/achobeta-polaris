package com.achobeta.api.dto.team;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

/**
 * @author yangzhiyao
 * @description 查询队员列表响应DTO
 * @date 2024/11/17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMemberListDTO {

    @FieldDesc(name = "实际成员列表")
    private Object members;

}
