package com.achobeta.api.dto.user;

import lombok.*;

/**
 * @author yangzhiyao
 * @description 用户查看个人信息请求DTO
 * @date 2024/11/6
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserInfoRequestDTO {

    private String userId;

}
