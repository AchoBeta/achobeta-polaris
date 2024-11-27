package com.achobeta.types.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 严豪哲
 * @Description: 访问个人私有资源权限注解
 * @Date: 2024/11/27 21:40
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfPermissionVerification {
}
