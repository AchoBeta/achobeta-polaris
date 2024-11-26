package com.achobeta.api.dto.user;

import com.achobeta.types.annotation.FieldDesc;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

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
public class QueryUserInfoRequestDTO implements Serializable {

    @NotBlank(message = "用户ID不能为空")
    @FieldDesc(name = "用户ID")
    private String userId;

}
