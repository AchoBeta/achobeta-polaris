package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

/**
 * @author yangzhiyao
 * @description 修改团队成员信息响应数据
 * @date 2024/11/17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyMemberInfoResponseDTO {

    @FieldDesc(name = "用户信息")
    Object userInfo;

}
