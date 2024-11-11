package com.achobeta.types.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author chensongmin
 * @description 自定义校验注解 -> 前缀校验
 * @date 2024/11/11
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrefixConstraintValidator.class)
public @interface PrefixConstraint {
    String value() default "" ;
    String message() default "{prefix.validate.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
