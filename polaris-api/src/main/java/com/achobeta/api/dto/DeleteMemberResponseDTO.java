package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import java.io.Serializable;

/**
 * @author yangzhiyao
 * @description 删除成员请求数据
 * @date 2024/11/21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberResponseDTO implements Serializable {

    @FieldDesc(name = "用户ID")
    private String userId;

    @FieldDesc(name = "成员ID")
    private String memberId;

    @FieldDesc(name = "团队ID")
    private String teamId;

}
