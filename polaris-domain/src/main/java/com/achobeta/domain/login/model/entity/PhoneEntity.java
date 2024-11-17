package com.achobeta.domain.login.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 严豪哲
 * @Description: 手机号实体类
 * @Date: 2024/11/6 21:23
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneEntity {

    private String phone;
}
