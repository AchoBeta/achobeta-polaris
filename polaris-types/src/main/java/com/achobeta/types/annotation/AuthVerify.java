package com.achobeta.types.annotation;

import java.lang.annotation.*;

/**
 * @author yangzhiyao
 * @description 鉴权注解
 * @date 2024/11/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthVerify {
    String value() default "";
}
