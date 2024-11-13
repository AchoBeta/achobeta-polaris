package com.achobeta.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author chensongmin
 * @description 业务模块枚举定义
 * @date 2024/11/11
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BizModule {
    RENDER("biz_001", "文本渲染模块"),

    ;

    private String code;
    private String name;
}