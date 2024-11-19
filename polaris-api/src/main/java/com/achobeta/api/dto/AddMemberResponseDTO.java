package com.achobeta.api.dto;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

/**
 * @author yangzhiyao
 * @description 添加团队成员信息响应数据
 * @date 2024/11/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMemberResponseDTO {

    @FieldDesc(name = "已存在的用户信息")
    Object userInfo;

    @FieldDesc(name = "状态码,0表示添加成功，1表示用户已存在")
    Integer statusCode;

}
