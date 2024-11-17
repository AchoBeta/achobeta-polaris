package com.achobeta.domain.login.model.bo;

import com.achobeta.domain.login.model.valobj.CodeVO;
import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 发送验证码的BO
 * @Date: 2024/11/6 21:18
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SendCodeBO {

    /*
     * 验证码值对象
     */
    CodeVO codeVO;

}
