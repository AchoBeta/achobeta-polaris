package com.achobeta.domain.login.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 严豪哲
 * @Description: 发送验证码响应值对象
 * @Date: 2024/11/6 21:25
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeVO {

    /*
     * 用户手机号
     */
    private String phone;

    /*
     * 验证码
     */
    private String code;

}
