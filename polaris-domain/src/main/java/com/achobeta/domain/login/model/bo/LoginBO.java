package com.achobeta.domain.login.model.bo;

import com.achobeta.domain.login.model.valobj.TokenVO;
import lombok.*;

/**
 * @Author: 严豪哲
 * @Description: 登录业务的BO
 * @Date: 2024/11/9 21:14
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginBO {

    /*
     * token值对象
     */
    TokenVO tokenVO;

    /*
     * 设备名称
     */
    String deviceName;

}
