package com.achobeta.api.dto;

import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 获取验证码的响应体
 * @Date: 2024/11/6 22:34
 * @Version: 1.0
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCodeResponseDTO {
    private String result;
}
