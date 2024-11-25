package com.achobeta.domain.user.model.bo;

import com.achobeta.domain.user.model.entity.UserEntity;
import lombok.*;

/**
 * @author yangzhiyao
 * @description User数据传输胶水类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserBO {

    private UserEntity userEntity;

}
