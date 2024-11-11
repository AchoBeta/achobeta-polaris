package com.achobeta.types.annotation;

/**
 * @author chensongmin
 * @description 字段描述注解，建议配置快捷键
 * @date 2024/11/11
 */
public @interface FieldDesc {

    String name() default "";

}
